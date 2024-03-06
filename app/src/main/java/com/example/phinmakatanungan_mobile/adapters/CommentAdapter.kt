package com.example.phinmakatanungan_mobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.models.Comment
import com.google.android.material.chip.Chip

class CommentAdapter(private var comments: List<Comment> = emptyList()) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    fun setComments(comments: List<Comment>) {
        // Filter out banned comments before setting the comments list
        this.comments = comments.filter { it.userData.account_status != "Banned" }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_commentContent)
        private val userNameTextView: TextView = itemView.findViewById(R.id.tv_userName)
        private val chip: Chip = itemView.findViewById(R.id.chip_course)

        fun bind(comment: Comment) {
            // Bind the comment data to the views
            val chipCourse = chip
            var cordep = ""

            if (comment.userData.user_role == "Teacher") {
                // Set chip color based on department for teachers
                when (comment.userData.department_id) {
                    "CITE" -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                        cordep = "CITE"
                    }
                    "CAHS" -> {
                        chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                        cordep = "BSN"
                    }
                    // Add more cases for other departments as needed
                    else -> chipCourse.setChipBackgroundColorResource(R.color.bg_color_alt)
                }
            } else if (comment.userData.user_role == "Student") {
                // Set chip color based on course for students
                when (comment.userData.course_id) {
                    "BSIT" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsit)
                    "BSN", "BSPharm", "BMLS", "BSPsych" -> chipCourse.setChipBackgroundColorResource(R.color.chip_bg_color_bsn)
                    // Add more cases for other courses as needed
                    else -> chipCourse.setChipBackgroundColorResource(R.color.bg_color_alt)
                }
            }
            chipCourse.text = cordep // Set chip text

            // Set user name and comment content
            val fullName = "${comment.userData.first_name} ${comment.userData.middle_name} ${comment.userData.last_name}"
            userNameTextView.text = fullName
            contentTextView.text = comment.content
        }
    }
}
