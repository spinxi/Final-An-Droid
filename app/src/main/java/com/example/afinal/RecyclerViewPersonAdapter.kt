package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afinal.Posts
import com.example.afinal.R

class RecyclerViewPersonAdapter(private val list: List<Posts>) :
    RecyclerView.Adapter<RecyclerViewPersonAdapter.PersonViewHolder>() {
    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView

        val textView: TextView
        init {
            imageView = itemView.findViewById(R.id.imageView)
            textView = itemView.findViewById(R.id.textView)

        }
        fun setData(posts: Posts){
            Glide.with(itemView.context)
                .load(posts.imageUrl)
                .into(imageView)
            textView.text = posts.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_post, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val posts = list[position]
        holder.setData(posts)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}