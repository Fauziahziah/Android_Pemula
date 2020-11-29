package com.example.mysubmissin2fundamental.viewModelAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.github
import kotlinx.android.synthetic.main.item_github_users.view.*
import java.util.*
import kotlin.collections.ArrayList



class ListGithubAdapter(private var list: ArrayList<github>) : RecyclerView.Adapter<ListGithubAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null



    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_github_users, viewGroup, false )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])

    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(Github: github){
            with(itemView){
                Glide.with(itemView.context)
                    .load(Github.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(item_photo)

                item_name.text = Github.username
                item_description.text = Github.name
                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(Github)}

            }
        }

    }

    interface OnItemClickCallback {
         fun onItemClicked(data: github)

    }

}
