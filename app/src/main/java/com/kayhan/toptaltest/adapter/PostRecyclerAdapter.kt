package com.kayhan.toptaltest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kayhan.toptaltest.R
import com.kayhan.toptaltest.roomdb.Post
import javax.inject.Inject

class PostRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter <PostRecyclerAdapter.PostViewHolder>(){

    private val diffUtil = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var posts: List<Post>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.postRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.postRowTitle)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.postRowAuthor)
        val yearText = holder.itemView.findViewById<TextView>(R.id.postRowLocation)
        val art = posts[position]
        holder.itemView.apply {
            glide.load(art.imageUrl).into(imageView)
            nameText.text = "Title: ${art.title}"
            artistNameText.text = "Author: ${art.author}"
            yearText.text = "Location: ${art.location}"
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}