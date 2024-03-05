package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.CommentAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.Comment
import com.example.phinmakatanungan_mobile.models.CommentResponse
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader


class CommentFragment : Fragment() {

    private var bottomNavigationView: BottomNavigationView? = null

    private lateinit var post: Post
    private lateinit var commentAdapter: CommentAdapter
    companion object {
        private const val ARG_POST = "post"

        fun newInstance(post: Post?): CommentFragment {
            val fragment = CommentFragment()
            val args = Bundle()
            args.putParcelable(ARG_POST, post)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Post>(ARG_POST)?.let { post ->
            this.post = post
        }

        // Initialize RecyclerView for displaying comments//

        val buttonSubmitComment = view.findViewById<ImageView>(R.id.buttonSubmitComment)
        buttonSubmitComment.setOnClickListener {
            addComment()
        }

        commentAdapter= CommentAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.comment_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = commentAdapter

        arguments?.getParcelable<Post>(ARG_POST)?.let { post ->
            this.post = post
            loadComments(post.id.toString())
        }

        //bottom nav will hide when in the comment section
        bottomNavigationView = activity?.findViewById(R.id.bottomNav);
        bottomNavigationView?.visibility = View.GONE

//        val commentBackBtn = view.findViewById<ImageView>(R.id.iv_commentBackBtn)
//        commentBackBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_commentFragment_to_dashboardFragment2)
//        }

    }

    private fun loadComments(postId: String) {
        PHINMAClient.instance.getComments(postId)
            .enqueue(object : Callback<CommentResponse> {
                override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                    if (response.isSuccessful) {
                        val commentResponse: CommentResponse? = response.body()
                        val comments: List<Comment> = commentResponse?.comments ?: emptyList()
                        commentAdapter.setComments(comments)
                    } else {
                        Log.e("CommentFragment", "Failed to fetch comments. Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("CommentFragment", "Error fetching comments: ${t.message}")
                }
            })
    }

    private fun addComment() {
        val editTextComment = requireView().findViewById<EditText>(R.id.editTextComment)
        val content = editTextComment.text.toString()
        editTextComment.text.clear()

        val sharedPreferences = context?.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences?.getString("userData", "")
        val userId = Gson().fromJson(userDataJson, UserData::class.java)?.user_id ?: ""

        if (userId.isNotEmpty()) {
            val postId = post.id.toString()
            addCommentToDatabase(postId, userId, content)
        } else {
            sharedPreferences?.let {
                if (!it.contains("userData")) {
                    Log.d("CommentFragment", "Key 'userData' not found in SharedPreferences")
                }
            }
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun addCommentToDatabase(postId: String, userId: String, content: String) {

        val signupDataJson = "{\"id\":\"$postId\",\"user_id\":\"$userId\",\"content\":\"$content\"}"

        try {
            val reader = JsonReader(StringReader(signupDataJson))
            reader.isLenient = true
            reader.beginObject()
            reader.close()
            PHINMAClient.instance.addComment(
                userId,
                content,
                postId,
            )
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()

                            loadComments(postId)
                        } else {
                            val errorMessage: String = try {
                                response.errorBody()?.string() ?: "Failed to get a valid response. Response code: ${response.code()}"
                            } catch (e: Exception) {
                                "Failed to get a valid response. Response code: ${response.code()}"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("API_RESPONSE", errorMessage)
                        }
                    }
                })
        } catch (e: Exception) {
            Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show the BottomNavigationView if it's not null
        bottomNavigationView?.visibility = View.VISIBLE
    }
}
