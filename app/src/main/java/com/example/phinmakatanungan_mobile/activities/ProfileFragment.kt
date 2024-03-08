package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.databinding.FragmentProfileBinding
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: SharedPrefsViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val userDataPrefKeys = "userData"

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

        binding.root.findViewById<ImageView>(R.id.iv_editProfileBtn).setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.posts.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_userPostsFragment)
        }

        val authToken = sharedPreferences.getString("authToken", "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the function to update user info from SharedPreferences
        updateUserInfoFromPrefs(requireContext())
    }
    private fun updateUserInfoFromPrefs(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString(userDataPrefKeys, null)

        if (userDataJson != null) {
            val userData = Gson().fromJson(userDataJson, UserData::class.java)
            updateInfo(userData, context)
        } else {
            Log.e("UserInfoUpdate", "User data not found in SharedPreferences")
        }
    }


    @SuppressLint("SetTextI18n")
    fun updateInfo(userData: UserData, context: Context) {
        val binding = // Obtain the binding object for your layout

            when (userData.user_role) {
                "Student" -> {
                    val fullName = "${userData.first_name} ${userData.middle_name} ${userData.last_name}"
                    binding.tvStudentName.text = fullName
                    binding.tvStudentID.text = userData.user_id
                    binding.tvStudentEmail.text = userData.email
                    binding.tvCourse.text = userData.course_id
                }
                "Teacher" -> {
                    binding.tvIDRole.text = "Teacher ID"
                    binding.tvDepCors.text = "Department"
                    val fullName = "${userData.first_name} ${userData.middle_name} ${userData.last_name}"
                    binding.tvStudentName.text = fullName
                    binding.tvStudentID.text = userData.user_id
                    binding.tvStudentEmail.text = userData.email
                    binding.tvCourse.text = userData.department_id
                }
                else -> {
                    binding.tvStudentName.text = "No data retrieved"
                    binding.tvStudentID.text = "No data retrieved"
                    binding.tvStudentEmail.text = "No data retrieved"
                    binding.tvCourse.text = "No data retrieved"
                }
            }
    }
    private fun signOut() {
        val myPreferenceEditor = requireContext().getSharedPreferences("myPreference", Context.MODE_PRIVATE).edit()
        myPreferenceEditor.remove("authToken").apply()
        val userDataPrefsEditor = requireContext().getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE).edit()
        userDataPrefsEditor.clear().apply()
        val filter = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE).edit()
        filter.clear().apply()
        // Redirect back to WelcomeActivity and clear the activity stack
        val intent = Intent(requireActivity(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()  // Finish MainActivity to prevent the user from navigating back
    }
}

