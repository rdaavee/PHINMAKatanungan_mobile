package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var phinmaApi: PHINMAApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter

        val sharedPreferences = requireActivity().getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE)

        PHINMAClient.setSharedPreferences(sharedPreferences)

        phinmaApi = PHINMAClient.instance

        fetchPosts()
    }

    private fun fetchPosts() {
        val call: Call<PostResponse> = phinmaApi.getPosts()

        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {

                    val posts: List<Post> = response.body() ?.posts ?: emptyList()

                    postAdapter.setPosts(posts)

                } else {
                    Log.e("DashboardFragment", "Failed to fetch posts. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("DashboardFragment", "Error fetching posts: ${t.message}")
            }
        })
    }
}