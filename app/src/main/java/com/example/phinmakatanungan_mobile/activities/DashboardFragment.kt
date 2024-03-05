package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
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
import com.example.phinmakatanungan_mobile.adapters.AnnouncementsAdapter
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.Announcement
import com.example.phinmakatanungan_mobile.models.AnnouncementResponse
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViews: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var announcementAdapter: AnnouncementsAdapter
    private lateinit var phinmaApi: PHINMAApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViews = view.findViewById(R.id.announcement_recyclerView)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViews.layoutManager = layoutManager
        announcementAdapter = AnnouncementsAdapter()
        recyclerViews.adapter = announcementAdapter

        recyclerView = view.findViewById(R.id.post_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter


        val sharedPreferences = requireActivity().getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE)

        PHINMAClient.setSharedPreferences(sharedPreferences)
        phinmaApi = PHINMAClient.instance
        getAnnouncements()
        fetchPosts()
    }

    private fun fetchPosts() {
        val call: Call<PostResponse> = phinmaApi.getPosts()

        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    val postResponse: PostResponse? = response.body()
                    val posts: List<Post> = postResponse?.posts ?: emptyList()

                    // Group posts first by department, then by course
                    val postsMap = posts.groupBy { it.user.department_id }
                        .mapValues { (_, posts) -> posts.groupBy { it.user.course_id } }

                    postAdapter.setPostsMap(postsMap)
                } else {
                    Log.e("DashboardFragment", "Failed to fetch posts. Code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("DashboardFragment", "Error fetching posts: ${t.message}")
            }
        })
    }


    private fun getAnnouncements() {
        val call: Call<AnnouncementResponse> = phinmaApi.getAnnounce()

        call.enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                if (response.isSuccessful) {
                    val announcementResponse: AnnouncementResponse? = response.body()
                    val announcements: List<Announcement> = announcementResponse?.announcements ?: emptyList()
                    val announcementsMap = announcements.groupBy { it.department }
                    announcementAdapter.setAnnouncementsMap(announcementsMap)
                } else {
                    Log.e("DashboardFragment", "Failed to fetch posts. Code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Log.e("DashboardFragment", "Error fetching posts: ${t.message}")
            }
        })
    }
}