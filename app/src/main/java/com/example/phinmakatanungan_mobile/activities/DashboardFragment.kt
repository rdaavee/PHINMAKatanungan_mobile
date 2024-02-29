package com.example.phinmakatanungan_mobile.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.models.Post
import java.math.BigInteger

class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val posts = listOf(
            Post(1, "John Doe", "Computer Science", "Title 1", "Content 1", BigInteger.valueOf(10), BigInteger.valueOf(5)),
            Post(2, "Jane Smith", "Mathematics", "Title 2", "Content 2", BigInteger.valueOf(20), BigInteger.valueOf(8)),
            // Add more posts as needed
        )

        postAdapter = PostAdapter(posts)
        recyclerView.adapter = postAdapter
    }

}