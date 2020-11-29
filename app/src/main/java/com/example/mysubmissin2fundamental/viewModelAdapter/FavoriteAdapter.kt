package com.example.mysubmissin2fundamental.viewModelAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmissin2fundamental.R
import com.example.mysubmissin2fundamental.model.github
import kotlinx.android.synthetic.main.activity_detail_github.view.*
import kotlinx.android.synthetic.main.item_fav.view.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var list = ArrayList<github>()
    set(list){
        if(list.size > 0){
            this.list.clear()
        }

        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun addItem(Github: github){
        this.list.add(Github)
        notifyItemInserted(this.list.size - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.list.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(Github: github){
            with(itemView){
                item_name.text = Github.name
                item_username.text = Github.username
                Glide.with(itemView.context)
                    .load(Github.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(avatar)

            }
        }


    }


}