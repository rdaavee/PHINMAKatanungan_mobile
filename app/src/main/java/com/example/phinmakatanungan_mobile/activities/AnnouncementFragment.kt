package com.example.phinmakatanungan_mobile.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.AnnouncementsAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.Announcement
import com.example.phinmakatanungan_mobile.models.AnnouncementResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var announcementAdapter: AnnouncementsAdapter
    private lateinit var phinmaApi: PHINMAApi
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavHidden = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.announcement_recyclerView)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        announcementAdapter = AnnouncementsAdapter()
        recyclerView.adapter = announcementAdapter

        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)

        var lastScrollY = 0

        view.findViewById<NestedScrollView>(R.id.nestedScrollView)?.setOnScrollChangeListener { _, _, _, _, scrollY ->
            if (scrollY > lastScrollY && !isBottomNavHidden) {
                // Scrolling down, hide bottom nav
                hideBottomNav()
            } else if (scrollY <= lastScrollY && isBottomNavHidden) {
                // Scrolling up, show bottom nav
                showBottomNav()
            }
            lastScrollY = scrollY
        }


        val sharedPreferences = requireActivity().getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE)
        PHINMAClient.setSharedPreferences(sharedPreferences)
        phinmaApi = PHINMAClient.instance
        getAnnouncements()
    }

    private fun hideBottomNav() {
        val translationY = bottomNavigationView.height.toFloat()
        ObjectAnimator.ofFloat(bottomNavigationView, "translationY", 0f, translationY).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        isBottomNavHidden = true
    }

    private fun showBottomNav() {
        ObjectAnimator.ofFloat(bottomNavigationView, "translationY", bottomNavigationView.translationY, 0f).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        isBottomNavHidden = false
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