package com.example.phinmakatanungan_mobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.models.Post
import com.google.android.material.chip.Chip

class PostAdapter(private var postsMap: Map<String, List<Post>> = emptyMap()) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var currentCourse: String? = ""

    fun setPostsMap(postsMap: Map<String, List<Post>>) {
        this.postsMap = postsMap
        notifyDataSetChanged()
    }

    fun setCurrentCourse(courseId: String?) {
        currentCourse = courseId
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (currentCourse.isNullOrEmpty()) {
            val allPosts = postsMap.values.flatten()
            val post = allPosts[position]
            holder.bind(post)
        } else {
            val posts = postsMap[currentCourse ?: ""] ?: return
            val post = posts[position]
            holder.bind(post)
        }
    }

    override fun getItemCount(): Int {
        return if (currentCourse.isNullOrEmpty()) {
            val allPosts = postsMap.values.flatten()
            allPosts.size
        } else {
            postsMap[currentCourse ?: ""]?.size ?: 0
        }
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chipCourse: Chip = itemView.findViewById(R.id.chip_course)
        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_userName)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_header)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_body)
        private val likesChip: Chip = itemView.findViewById(R.id.chip_like)
        private val commentsChip: Chip = itemView.findViewById(R.id.chip_comment)
        private val commentCount: TextView = itemView.findViewById(R.id.comment_count)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)

        fun bind(post: Post) {
            val chipCourse = chipCourse

            when (post.user.course_id) {
                //cite
                "BSIT"  -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                //cahs
                "BSN","BSPharm","BMLS","BSPsych" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                //cea
                "BSCE","BSEE","BSArch","BSCpE","BSME" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsce)
                //ccje
                "BSCrim", -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bscrim)
                //cela
                "ABComm","ABPolSci","BSEEduc","BSED" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsed)
                //cma
                "BSA","BSMA","BSAT","BSHM","BSTM","BSBA" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsba)
                //shs

                else -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
            }

            val fullName = "${post.user.first_name} ${post.user.middle_name} ${post.user.last_name}"
            userNameTextView.text = fullName
            chipCourse.text = post.user.course_id
            titleTextView.text = post.title
            contentTextView.text = post.content
            likeCount.text = post.likes_count.toString()
            commentCount.text = post.comments_count.toString()
        }
    }
}

