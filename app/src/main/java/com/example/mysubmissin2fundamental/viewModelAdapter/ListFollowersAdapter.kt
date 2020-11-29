package com.example.mysubmissin2fundamental.viewModelAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.FollowerGithub
import kotlinx.android.synthetic.main.item_github_users.view.*

class ListFollowersAdapter(private val listFollowers: ArrayList<FollowerGithub>) : RecyclerView.Adapter<ListFollowersAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_github_followers, viewGroup, false)
        return ListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = listFollowers.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(follower : FollowerGithub){
            with(itemView){
                Glide.with(itemView.context)
                    .load(follower.avatarfollowers)
                    .apply(RequestOptions().override(55,55))
                    .into(item_photo)
       item_name.text = follower.usernamefollowers
                item_description.text = follower.namefollowers
            }
        }

    }

}