package com.example.phinmakatanungan_mobile.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.models.Post
import com.google.android.material.chip.Chip

class PostAdapter(private var posts: List<Post> = emptyList()) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        Log.d("PostAdapter", "Binding post: $post")
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
            val chipCourse = chipCourse

            when (post.user.course_id) {
                "BSIT"  -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                "BSN" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                "BSCE" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsce)
                "BSED" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsed)
                "BSBA" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsba)

                else -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
            }

        val fullName = "${post.user.first_name} ${post.user.middle_name} ${post.user.last_name}"
            userNameTextView.text = fullName
            chipCourse.text = post.user.course_id
//            chipCourse.chipBackgroundColor=
            titleTextView.text = post.title
            contentTextView.text = post.content
            likesChip.text = post.likes_count.toString()
            commentsChip.text = post.comments_count.toString()
        }
    }
}
