package com.example.phinmakatanungan_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private var currentCourse: String? = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setPostsMap(postsMap: Map<String, Map<String, List<Post>>>) {
        this.postsMap = postsMap
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentDepartment(departmentId: String?) {
        currentDepartment = departmentId
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
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
        val posts = when {
            currentDepartment.isNullOrEmpty() && currentCourse.isNullOrEmpty() -> {
                // Display all posts when both department and course are null
                postsMap.values.flatMap { it.values }.flatten()
            }
            currentDepartment.isNullOrEmpty() -> {
                // Display all posts with the current course
                emptyList() // You can't set the course alone, so return empty list
            }
            currentCourse.isNullOrEmpty() -> {
                // Display all posts with the current department
                postsMap[currentDepartment ?: ""]?.values?.flatten() ?: emptyList()
            }
            else -> {
                // Display posts based on the set department and course
                postsMap[currentDepartment ?: ""]?.get(currentCourse ?: "") ?: emptyList()
            }
        }

        val post = posts.getOrNull(position)
        post?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        val posts = when {
            currentDepartment.isNullOrEmpty() && currentCourse.isNullOrEmpty() -> {
                // If both department and course are null, count all posts
                postsMap.values.flatMap { it.values }.flatten()
            }
            currentDepartment.isNullOrEmpty() -> {
                // If department is null, it means course is set alone, so return 0
                emptyList()
            }
            currentCourse.isNullOrEmpty() -> {
                // If only course is null, count all posts for the given department
                postsMap[currentDepartment ?: ""]?.values?.flatten() ?: emptyList()
            }
            else -> {
                // Count posts based on the set department and course
                postsMap[currentDepartment ?: ""]?.get(currentCourse ?: "") ?: emptyList()
            }
        }
        return posts.size
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

        init {
            commentsChip.setOnClickListener {
                val post = if (currentCourse.isNullOrEmpty() && currentDepartment.isNullOrEmpty()) {
                    // Display all posts when both course and department are null
                    val allPosts = postsMap.values.flatMap { it.values }.flatten()
                    allPosts.getOrNull(adapterPosition)
                } else if (currentDepartment.isNullOrEmpty()) {
                    // Display posts based on the set course when department is null
                    val coursePosts =
                        postsMap[currentCourse ?: ""]?.values?.flatten() ?: emptyList()
                    coursePosts.getOrNull(adapterPosition)
                } else {
                    // Display posts based on the set course and department
                    val departmentPosts =
                        postsMap[currentCourse ?: ""]?.get(currentDepartment ?: "") ?: emptyList()
                    departmentPosts.getOrNull(adapterPosition)
                }
                val fragment = CommentFragment.newInstance(post)
                val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

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
                else -> chipCourse.text = "Null" // Default case
            }

            val fullName = "${post.user.first_name} ${post.user.middle_name} ${post.user.last_name}"
            userNameTextView.text = fullName
            titleTextView.text = post.title
            contentTextView.text = post.content
            likeCount.text = post.likes_count.toString()
            commentCount.text = post.comments_count.toString()
        }
    }
}




