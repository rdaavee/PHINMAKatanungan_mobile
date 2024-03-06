package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.databinding.FragmentDepartmentBinding
import com.example.phinmakatanungan_mobile.databinding.FragmentProfileBinding

class DepartmentFragment : Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: FragmentDepartmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentDepartmentBinding.inflate(inflater, container, false)

        // Initialize postAdapter
        postAdapter = PostAdapter()

        return binding.root
    }

    private fun setData(dept: String, courses: List<String>) {
        // Store department and courses in SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("department", dept)
        editor.putStringSet("courses", courses.toSet())
        editor.apply()

        // Update UI or perform any other actions
        Log.d("DepartmentFragment", "Setting data - Department: $dept, Courses: $courses")
    }
}
