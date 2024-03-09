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
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient


class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var phinmaApi: PHINMAApi
    private lateinit var viewModel: DashboardViewModel
    private lateinit var searchView: SearchView
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

        // Initialize the ViewModel after initializing phinmaApi
        val factory = DashboardViewModelFactory(phinmaApi)
        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)

        // Observe the posts LiveData
        viewModel.posts.observe(viewLifecycleOwner, Observer { postsMap ->
            // Update the UI with the new posts data
            postAdapter.setPostsMap(postsMap)
        })
        viewModel.fetchPosts()

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter posts based on the new search query
                postAdapter.setSearchQuery(newText)
                return true
            }
        })
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