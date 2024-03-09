package com.example.phinmakatanungan_mobile.activities

import UserPostsAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.databinding.FragmentUserPostsBinding
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPostsFragment : Fragment() {

    private lateinit var binding: FragmentUserPostsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: UserPostsAdapter
    private var posts: List<Post> = emptyList()
    private lateinit var phinmaApi: PHINMAApi
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPostsBinding.inflate(inflater,container,false)
        phinmaApi = PHINMAClient.instance
        val preference = requireActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE)
        val authToken = preference.getString("authToken", "")
        recyclerView = binding.postRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = UserPostsAdapter()
        recyclerView.adapter = postAdapter

        fetchPosts(authToken.toString())
        return binding.root
    }
    private fun fetchPosts(authToken: String) {
        val call: Call<PostResponse> = phinmaApi.getPosts("Bearer $authToken")
        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    try {
                        val postResponse: PostResponse? = response.body()
                        val posts: List<Post> = postResponse?.posts ?: emptyList()

                        updatePosts(posts)
                    } catch (e: NullPointerException) {
                        Log.e("UserPostsAdapter", "Error occurred: ${e.message}")
                    }
                } else {
                    Log.e("UserPostsAdapter", "Error occurred")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("UserPostsAdapter", "Failed to fetch posts", t)
            }
        })
    }
    private fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
        recyclerView.adapter?.notifyDataSetChanged()
    }
}