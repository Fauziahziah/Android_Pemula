package com.example.mysubmissin2fundamental.viewActivity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.FollowingGithub
import com.example.mysubmissin2fundamental.viewModelAdapter.ListFollowingAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following_.*
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 */
class Following_Fragment : Fragment() {
    private val list = ArrayList<FollowingGithub>()

    companion object{
        private val TAG = FollowersFragment::class.java.simpleName
        private val ARG_USERNAME = "username"

        fun newInstanse(username: String): Following_Fragment {
            val fragment =
                Following_Fragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        following.setHasFixedSize(true)
        val username = arguments?.getString(ARG_USERNAME)
        getListFollowing(username.toString())

    }

    private fun getListFollowing(id: String){
       // progressBarFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 9f128c2a9de350c73358cc0c0b645e52a15950bc" )
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id/following"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
          //    progressBarFollowing.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val name = jsonObject.getString("html_url")
                        val Following =
                            FollowingGithub()
                        Following.usernamefollowing = username
                        Following.avatarfollowing = avatar
                        Following.namefollowing = name
                        list.add(Following)
                    }
                    showRecyclerList()
                    showLoading(false)
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
          //    progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    402 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
              showLoading(false)
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun showRecyclerList(){
        following.layoutManager = LinearLayoutManager(activity)
        val listFollowingAdapter =
            ListFollowingAdapter(
                list
            )
        following.adapter = listFollowingAdapter
    }

    private fun showLoading(state: Boolean){
        if(state) {
            progressBarFollowing.visibility = View.VISIBLE
        }   else {
            progressBarFollowing.visibility = View.GONE
        }
    }

}
