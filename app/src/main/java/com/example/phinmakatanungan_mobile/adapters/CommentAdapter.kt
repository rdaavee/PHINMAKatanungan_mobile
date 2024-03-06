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
        this.comments = comments
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

            val chipCourse = chip
            var cordep = ""

            if(comment.userData.user_role == "Teacher"){
                when (comment.userData.department_id) {
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
            else if(comment.userData.user_role == "Student"){
                when (comment.userData.course_id) {
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
            when (comment.userData.user_role) {
                "Teacher" -> chipCourse.text = cordep
                "Student" -> chipCourse.text = comment.userData.course_id
                else -> chipCourse.text = "Null" // Default case
            }

            val fullName = "${comment.userData.first_name} ${comment.userData.middle_name} ${comment.userData.last_name}"
            userNameTextView.text = fullName
            contentTextView.text = comment.content
        }
    }
}
