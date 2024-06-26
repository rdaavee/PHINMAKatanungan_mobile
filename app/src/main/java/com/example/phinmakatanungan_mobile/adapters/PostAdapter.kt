package com.example.phinmakatanungan_mobile.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.activities.CommentFragment
import com.example.phinmakatanungan_mobile.models.Post
import com.google.android.material.chip.Chip
import kotlin.collections.*
import kotlin.collections.flatten
class PostAdapter(private var postsMap: Map<String, Map<String, List<Post>>> = emptyMap()) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var currentDepartment: String? = ""
    private var currentCourses: List<String>? = listOf()
    private var searchQuery: String? = null // Add searchQuery variable


    @SuppressLint("NotifyDataSetChanged")
    fun setPostsMap(postsMap: Map<String, Map<String, List<Post>>>) {
        this.postsMap = postsMap
        notifyDataSetChanged()
    }
    fun setSearchQuery(query: String?) { // Add method to set search query
        searchQuery = query
        notifyDataSetChanged()
    }

    fun setCurrentDepartment(departmentId: String?) {
        currentDepartment = departmentId
        notifyDataSetChanged()
        getCurrentPosts()
        Log.d("PostAdapter", "Current department set: $currentDepartment")
    }

    fun setCurrentCourses(courseIds: List<String>?) {
        currentCourses = courseIds
        notifyDataSetChanged()
        getCurrentPosts()
        Log.d("PostAdapter", "Current courses set: $currentCourses")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val posts = getCurrentPosts()
        val post = posts.getOrNull(position)
        post?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        val posts = getCurrentPosts()
        return posts.size
    }

    private fun getCurrentPosts(): List<Post> {
        val allPosts = when {
            currentDepartment.isNullOrEmpty() && currentCourses.isNullOrEmpty() ->
                postsMap.values.flatMap { it.values }.flatten()
            currentDepartment.isNullOrEmpty() ->
                postsMap.values.flatMap { departmentPosts ->
                    currentCourses?.flatMap { courseId ->
                        departmentPosts[courseId] ?: emptyList()
                    } ?: emptyList()
                }
            currentCourses.isNullOrEmpty() ->
                postsMap[currentDepartment ?: ""]?.values?.flatten() ?: emptyList()
            else ->
                postsMap[currentDepartment ?: ""]
                    ?.filterKeys { currentCourses?.contains(it) == true }
                    ?.values
                    ?.flatten() ?: emptyList()
        }

        val currentSearchQuery = searchQuery

        return if (currentSearchQuery.isNullOrBlank()) {
            allPosts.filter { it.user.account_status == "Active" }
                .sortedByDescending { it.timestamp }
        } else {
            allPosts.filter { post ->
                post.title.contains(currentSearchQuery, ignoreCase = true)
            }.filter { it.user.account_status == "Active" }
                .sortedByDescending { it.timestamp }
        }
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chipCourse: Chip = itemView.findViewById(R.id.chip_course)
        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_userName)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_header)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_body)
        private val commentsChip: Chip = itemView.findViewById(R.id.chip_comment)
        private val commentCount: TextView = itemView.findViewById(R.id.comment_count)
        fun bind(post: Post) {
            val chipCourse = chipCourse
            var cordep = ""

            if(post.user.user_role == "Teacher"){
                when (post.user.department_id) {
                    // Add cases for each department
                    // For example:
                    "CITE"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                        cordep = "CITE"
                    }
                    "CAHS"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                        cordep = "BSN"
                    }
                    "CEA"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsce)
                        cordep = "CEA"
                    }
                    "CMA"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsba)
                        cordep = "CMA"
                    }
                    "CCJE"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bscrim)
                        cordep = "CCJE"
                    }
                    "SHS"  -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                        cordep = "SHS"
                    }
                    // Add more cases for other departments as needed
                    else -> chipCourse.setChipBackgroundColorResource(R.color.bg_color_alt)
                }
            }
            else if(post.user.user_role == "Student"){
                when (post.user.course_id) {
                    //cite
                    "BSIT" -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                    }
                    //cahs
                    "BSN", "BSPharm", "BMLS", "BSPsych" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                    //cea
                    "BSCE", "BSEE", "BSArch", "BSCpE", "BSME" -> chipCourse.setChipBackgroundColorResource(
                        R.color.chip_bg_color_bsce
                    )
                    //ccje
                    "BSCrim" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bscrim)
                    //cela
                    "ABComm", "ABPolSci", "BSEEduc", "BSED" -> chipCourse.setChipBackgroundColorResource(
                        R.color.chip_bg_color_bsed
                    )
                    //cma
                    "BSA", "BSMA", "BSAT", "BSHM", "BSTM", "BSBA" -> chipCourse.setChipBackgroundColorResource(
                        R.color.chip_bg_color_bsba
                    )
                    //shs
                    else -> chipCourse.setChipBackgroundColorResource(R.color.bg_color_alt)
                }
            }
            when (post.user.user_role) {
                "Teacher" -> chipCourse.text = cordep
                "Student" -> chipCourse.text = post.user.course_id
                else -> chipCourse.text = "" // Default case
            }

            val fullName = "${post.user.first_name} ${post.user.middle_name} ${post.user.last_name}"
            userNameTextView.text = fullName
            titleTextView.text = post.title
            contentTextView.text = post.content
            commentCount.text = post.comments_count.toString()


        }
        init {
            commentsChip.setOnClickListener {
                val post = getCurrentPosts().getOrNull(adapterPosition)

                if (post != null) {
                    val fragment = CommentFragment.newInstance(post)
                    val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment, fragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    Log.e("PostViewHolder", "Post is null")
                    Log.d("PostViewHolder", "currentDepartment: $currentDepartment, currentCourse: $currentCourses, adapterPosition: $adapterPosition")
                    // Handle the case where post is null (e.g., show a message to the user)
                    Toast.makeText(itemView.context, "Post is null", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Method to find a post by its ID
    fun findPostById(postId: String): Post? {
        val postIdInt = postId.toIntOrNull() ?: return null

        for ((_, departmentPosts) in postsMap) {
            for ((_, coursePosts) in departmentPosts) {
                for (post in coursePosts) {
                    if (post.id == postIdInt) {
                        return post
                    }
                }
            }
        }
        // Return null if no post with the given ID is found
        return null
    }

    override fun getItemId(position: Int): Long {
        val posts = getCurrentPosts()
        if (position in posts.indices) {
            // Convert the ID of the post at the given position to Long
            return posts[position].id.toLong()
        }
        // Return RecyclerView.NO_ID if the position is out of bounds
        return RecyclerView.NO_ID
    }
}