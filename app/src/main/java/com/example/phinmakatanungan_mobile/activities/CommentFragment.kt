package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class CommentFragment : Fragment() {

    private lateinit var post: Post
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

        val buttonSubmitComment = view.findViewById<Button>(R.id.buttonSubmitComment)
        buttonSubmitComment.setOnClickListener {
            addComment()
        }
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
        val test_comment = "This is a test comment"

        val signupDataJson = "{\"id\":\"$postId\",\"user_id\":\"$userId\",\"content\":\"$content\"}"

        try {
            val reader = JsonReader(StringReader(signupDataJson))
            reader.isLenient = true
            reader.beginObject()
            reader.close()
            PHINMAClient.instance.addComment(
                userId,
                content,
                postId,)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()
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
}
