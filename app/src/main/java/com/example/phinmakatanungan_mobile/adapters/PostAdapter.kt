package com.example.phinmakatanungan_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.models.Post
import com.google.android.material.chip.Chip

class PostAdapter(private val posts: List<Post> = emptyList()) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chipCourse: Chip = itemView.findViewById(R.id.chip_course)
        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_userName)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_header)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_body)
        private val likesChip: Chip = itemView.findViewById(R.id.chip_like)
        private val commentsChip: Chip = itemView.findViewById(R.id.chip_comment)

        fun bind(post: Post) {
            userNameTextView.text = post.name
            chipCourse.text = post.course
            titleTextView.text = post.title
            contentTextView.text = post.content
            // Update likes and comments count
            likesChip.text = post.likes_count.toString()
            commentsChip.text = post.comments_count.toString()
        }
    }
}

// Method t
