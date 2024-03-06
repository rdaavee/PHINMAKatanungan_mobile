package com.example.phinmakatanungan_mobile.activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.AnnouncementsAdapter
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.Announcement
import com.example.phinmakatanungan_mobile.models.AnnouncementResponse
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import kotlinx.coroutines.NonCancellable.start
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var phinmaApi: PHINMAApi
    private lateinit var viewModel: DashboardViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavHidden = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize PHINMAApi instance first
        val sharedPreferences = requireActivity().getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE)
        PHINMAClient.setSharedPreferences(sharedPreferences)
        phinmaApi = PHINMAClient.instance

        recyclerView = view.findViewById(R.id.post_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter

        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)

        // Initialize the ViewModel after initializing phinmaApi
        val factory = DashboardViewModelFactory(phinmaApi)
        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)

        // Observe the posts LiveData
        viewModel.posts.observe(viewLifecycleOwner, Observer { postsMap ->
            // Update the UI with the new posts data
            postAdapter.setPostsMap(postsMap)
        })

        // bottom nav will hide and show when you scroll vertically
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

        viewModel.fetchPosts()

//        bottomNavigationView.menu.findItem(R.id.dashboardFragment)?.setOnMenuItemClickListener {
//            scrollToTop()
//            true
//        }
    }

//    private fun scrollToTop() {
//        val nestedScrollView = view?.findViewById<NestedScrollView>(R.id.nestedScrollView)
//        nestedScrollView?.smoothScrollTo(0, 0) // Scroll to the top
//    }


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
}