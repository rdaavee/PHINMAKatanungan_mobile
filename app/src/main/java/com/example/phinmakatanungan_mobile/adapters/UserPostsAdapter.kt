import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.activities.CommentFragment
import com.example.phinmakatanungan_mobile.models.Post
import com.google.android.material.chip.Chip
class UserPostsAdapter : RecyclerView.Adapter<UserPostsAdapter.PostViewHolder>() {

    private var posts: List<Post> = emptyList() // Initialize with an empty list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_dashboard_posts, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // Update dataset using DiffUtil for efficient updates
    fun setPosts(newPosts: List<Post>) {
        val diffCallback = PostDiffCallback(posts, newPosts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        posts = newPosts
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentsChip: Chip = itemView.findViewById(R.id.chip_comment)
        private val commentCount: TextView = itemView.findViewById(R.id.comment_count)

        init {
            commentsChip.setOnClickListener {
                val context = itemView.context
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val post = posts[position]
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                    val commentFragment = CommentFragment.newInstance(post)
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragment, commentFragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    Log.e("UserPostsAdapter", "Invalid post position")
                }
            }
        }

        fun bind(post: Post) {
            // Your existing bind logic here

            // Handle comment count
            commentCount.text = post.comments_count.toString()
        }
    }

    // DiffUtil callback for calculating the difference between two lists of posts
    private class PostDiffCallback(
        private val oldList: List<Post>,
        private val newList: List<Post>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
