package com.example.mysubmissin2fundamental.viewActivity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.db.DatabaseContract
import com.example.mysubmissin2fundamental.db.GithubHelper
import com.example.mysubmissin2fundamental.model.github
import com.example.mysubmissin2fundamental.viewModelAdapter.SectionFollowAdapter
import kotlinx.android.synthetic.main.activity_detail_github.*
import kotlinx.android.synthetic.main.layout_fragment.*

class DetailGithubActivity : AppCompatActivity(), View.OnClickListener {
    private var isFavorite = false
    private var Github: github? = null
    private var position: Int = 0
    private lateinit var githubHelper : GithubHelper


    companion object{
        const val EXTRA_PERSON = "extra_person"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_github)

        githubHelper = GithubHelper.getInstance(applicationContext)
        githubHelper.open()

        Github = intent.getParcelableExtra(EXTRA_PERSON)
        if(Github != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isFavorite = true
        } else {
            Github = github()
        }


        val dataGithub = intent.getParcelableExtra<github>(EXTRA_PERSON) as github
        val sectionFollowAdapter =
            SectionFollowAdapter(
                this,
                supportFragmentManager
            )
        sectionFollowAdapter.username = dataGithub?.username
        viewPager.adapter = sectionFollowAdapter

        supportActionBar?.title = dataGithub.username
        name.text = dataGithub.username
        username.text = dataGithub.name
        Glide.with(DetailGithubActivity@this)
            .load(dataGithub.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(avatar)

        viewPager.adapter = sectionFollowAdapter
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f

       btn_Fav.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.btn_Fav){

            val avatar = avatar.toString().trim()
            val name = name.text.toString().trim()
            val username = username.text.toString().trim()

            Github?.avatar = avatar
            Github?.name = name
            Github?.username = username

            val intent = Intent(this@DetailGithubActivity, FavoriteActivity::class.java)
            intent.putExtra(EXTRA_PERSON, Github)
            intent.putExtra(EXTRA_POSITION, position)

            val values = ContentValues()
            values.put(DatabaseContract.GithubColumns.AVATAR, avatar)
            values.put(DatabaseContract.GithubColumns.USERNAME, username)
            values.put(DatabaseContract.GithubColumns.NAME, name)

            val result = githubHelper.insert(values)

            if (result > 0){
                Github?.id = result.toInt()
                setResult(RESULT_ADD, intent)
                finish()
            } else {
                Toast.makeText(this@DetailGithubActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
