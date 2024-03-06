package com.example.phinmakatanungan_mobile.activities

import android.os.Bundle
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
        val dept = ""
        val courses:List<String> = listOf()

        return binding.root
    }
    fun setData(dept: String, courses: List<String>){
        postAdapter.setCurrentDepartment(dept)
        postAdapter.setCurrentCourses(courses)
    }
}