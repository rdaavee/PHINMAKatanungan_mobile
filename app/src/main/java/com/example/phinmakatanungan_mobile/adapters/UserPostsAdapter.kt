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

class UserPostsAdapter(private var posts: List<Post>, private val userId: String) :
    RecyclerView.Adapter<UserPostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
    fun setPosts(posts: List<Post>) {
        this.posts = posts.filter { it.user.user_id == userId }
        notifyDataSetChanged()
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
                val context = itemView.context
                val post = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { posts[it] }
                post?.let {
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                    val commentFragment = CommentFragment.newInstance(post)
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment, commentFragment)
                        .addToBackStack(null)
                        .commit()
                } ?: Log.e("UserPostsAdapter", "Invalid post position")
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
