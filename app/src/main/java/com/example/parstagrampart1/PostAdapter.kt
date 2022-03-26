package com.example.parstagrampart1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val posts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.rv_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post: Post = posts.get(position)
        holder.rv_user.text = post.getUser().toString()
        holder.rv_description.text = post.getDescription().toString()
        holder.rv_time.text = post.getTime().toString()
        Glide.with(holder.itemView).load(post.getImage()).into(holder.rv_imageView)
    }

    override fun getItemCount() = posts.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val rv_imageView = itemView.findViewById<ImageView>(R.id.rv_imageView)
        val rv_user = itemView.findViewById<TextView>(R.id.rv_user)
        val rv_time = itemView.findViewById<TextView>(R.id.rv_time)
        val rv_description = itemView.findViewById<TextView>(R.id.rv_description)


    }
}