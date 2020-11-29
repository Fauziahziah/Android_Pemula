package com.example.mysubmissin2fundamental.viewActivity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.github
import com.example.mysubmissin2fundamental.viewModelAdapter.ListGithubAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG  =  MainActivity::class.java.simpleName
    }

    private var list = ArrayList<github>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_user.setHasFixedSize(true)
        showRecyclerList()
        getListGithubs()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.seacrh).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.seacrh_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean{
                //Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                getDataUserFromApi(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getDataUserFromApi(newText)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change){
            val mIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }

   fun getDataUserFromApi(username: String) {
       progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username "
        client.addHeader("Authorization", "token 9f128c2a9de350c73358cc0c0b645e52a15950bc" )
        client.addHeader("User-Agent", "request")
        client.get(url, object :AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
               progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                list.clear()
                try{
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val name = item.getString("html_url")
                        val Github =
                            github()
                        Github.username = username
                        Github.avatar = avatar
                        Github.name = name
                        list.add(Github)
                    }
                    showRecyclerList()

                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
             progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }

                //Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })

    }


      private fun getListGithubs(){

     progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users"
          client.addHeader("Authorization", "token 9f128c2a9de350c73358cc0c0b645e52a15950bc")
          client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
               progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                list.clear()
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username= jsonObject.getString("login")
                        getListGithubsDetail(username)
                    }

                }catch (e: Exception)  {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
              progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }

                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getListGithubsDetail(username: String){
         progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token 9f128c2a9de350c73358cc0c0b645e52a15950bc")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                        val jsonObject = JSONObject(result)
                        val id : Int = 0
                        val username = jsonObject.getString("login")
                        val avatar= jsonObject.getString("avatar_url")
                        val name = jsonObject.getString("name")
                        list.add(
                            github (id,
                                username,
                                avatar,
                                name
                            )
                        )
                        showRecyclerList()
                }catch (e: Exception)  {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                 progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }

                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun showRecyclerList(){
        rv_user.layoutManager = LinearLayoutManager(this)
        val listGithubAdapter =
            ListGithubAdapter(
                list
            )
        rv_user.adapter = listGithubAdapter

        listGithubAdapter.setOnItemClickCallback(object : ListGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: github) {
                showSelectedgithub(data)

            }
        })
    }

    private fun showSelectedgithub(Github: github){
        val moveIntent = Intent (this@MainActivity, DetailGithubActivity::class.java)
        moveIntent.putExtra(DetailGithubActivity.EXTRA_PERSON, Github)
        startActivity(moveIntent)
    }

    private fun showLoading(state: Boolean){
        if (state){
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
