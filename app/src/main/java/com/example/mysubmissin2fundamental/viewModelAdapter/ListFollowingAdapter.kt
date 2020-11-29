package com.example.mysubmissin2fundamental.viewModelAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.FollowingGithub
import kotlinx.android.synthetic.main.item_github_following.view.*

class ListFollowingAdapter(private val listfollowing: ArrayList<FollowingGithub>) : RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_github_following, viewGroup, false)
        return ListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = listfollowing.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listfollowing[position])
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(followingGithub: FollowingGithub){
            with(itemView){
                Glide.with(itemView.context)
                    .load(followingGithub.avatarfollowing)
                    .apply(RequestOptions().override(55,55))
                    .into(item_photo)
                item_name.text = followingGithub.usernamefollowing
                item_description.text = followingGithub.namefollowing
            }
        }

    }
}