package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.databinding.FragmentProfileBinding
import com.example.phinmakatanungan_mobile.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: SharedPrefsViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SharedPrefsViewModel::class.java)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE)
        // Handle sign out button click
        binding.root.findViewById<LinearLayout>(R.id.ll_seventh).setOnClickListener {
            signOut()
        }

        val authToken = (requireActivity() as MainActivity).getAuthToken()
        authToken?.let { getUserInfo(it) }

        return binding.root
    }

    private fun getUserInfo(authToken: String) {
        PHINMAClient.instance.getUserProfile("Bearer$authToken").enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    try {
                        val userData = response.body()
                        if (userData != null) {
                            updateInfo(userData)
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileFragment", "Exception while processing user data", e)
                        Toast.makeText(requireContext(), "Failed to process user info", Toast.LENGTH_SHORT).show()
                    }

                    val responseBody = response.body().toString()
                    Log.d("Response", responseBody)
                } else {
                    Toast.makeText(requireContext(), "Failed to get user info", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e("ProfileFragment", "Failed to get user info", t)
                Toast.makeText(requireContext(), "Failed to get user info", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateInfo(userData: UserData){
        val fullName = "${userData.first_name} ${userData.middle_name} ${userData.last_name}"
        binding.tvStudentName.text = fullName
        binding.tvStudentID.text = userData.student_id
        binding.tvStudentEmail.text = userData.email
        binding.tvCourse.text = userData.course_id
    }

    private fun signOut() {
        // Remove the token from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect back to WelcomeActivity and clear the activity stack
        val intent = Intent(requireActivity(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()  // Finish MainActivity to prevent the user from navigating back
    }
}

