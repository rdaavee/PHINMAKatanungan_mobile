package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.databinding.FragmentCreatePostBinding
import com.example.phinmakatanungan_mobile.databinding.FragmentProfileBinding
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.gson.Gson

class CreatePostFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val USER_DATA_PREFS_KEY = "userData"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the function to update user info from SharedPreferences
        updateUserInfoFromPrefs(requireContext())
    }
    //display the name
    private fun updateUserInfoFromPrefs(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString(USER_DATA_PREFS_KEY, null)
        if (userDataJson != null) {
            val userData = Gson().fromJson(userDataJson, UserData::class.java)
            updateInfo(userData, context)
        } else {
            Log.e("UserInfoUpdate", "User data not found in SharedPreferences")
        }
    }
    fun updateInfo(userData: UserData, context: Context) {
        val fullName = "${userData.first_name} ${userData.middle_name} ${userData.last_name}"
        binding.tvFullName.text = fullName
    }
}