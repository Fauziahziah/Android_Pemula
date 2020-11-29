package com.example.mysubmissin2fundamental.viewActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.db.GithubHelper
import com.example.mysubmissin2fundamental.helper.MappingHelper
import com.example.mysubmissin2fundamental.model.github
import com.example.mysubmissin2fundamental.viewModelAdapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    private lateinit var githubHelper: GithubHelper


    companion object{
        const val REQUEST_ADD = 100
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title="Favorite"

        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        rv_favorite.adapter = adapter

        githubHelper = GithubHelper.getInstance(applicationContext)
        githubHelper.open()

        if (savedInstanceState == null){

        loadGithubsAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<github>(EXTRA_STATE)
            if (list !=null){
                adapter.list = list
            }
        }

    }

    private fun loadGithubsAsync(){
        GlobalScope.launch(Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val deferredGithub = async(Dispatchers.IO){
                val cursor = githubHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)

            }

            progressBar.visibility = View.INVISIBLE
            val fav = deferredGithub.await()
            if(fav.size > 0){
                adapter.list = fav
            } else {
                adapter.list = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini ")
            }




        }
    }




    override fun onDestroy() {
        super.onDestroy()
        githubHelper.close()
    }

    private fun showSnackbarMessage(message: String){
        Snackbar.make(rv_favorite, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.list)
    }



}