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
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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
import com.google.android.material.card.MaterialCardView
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



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val sharedPreferences = requireActivity().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val dept = sharedPreferences.getString("department", null)
        val courses = sharedPreferences.getStringSet("courses", emptySet())?.toList()
        PHINMAClient.setSharedPreferences(sharedPreferences)
        phinmaApi = PHINMAClient.instance

        recyclerView = view.findViewById(R.id.post_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter

        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)

        if (dept != null && courses != null) {
            postAdapter.setCurrentDepartment(dept)
            postAdapter.setCurrentCourses(courses)
        } else {
            // Handle case where data is not available
            Log.e("DashboardFragment", "No data found in SharedPreferences")
        }

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Call fetchPosts with the updated query
                viewModel.fetchPosts(newText)
                return true
            }
        })

        // Initialize the ViewModel after initializing phinmaApi
        val factory = DashboardViewModelFactory(phinmaApi)
        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)

        // Observe the posts LiveData
        viewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            // Update the UI with the new posts data
            postAdapter.setPosts(posts)
        })

        // Fetch initial posts without any filter
        viewModel.fetchPosts()
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
}