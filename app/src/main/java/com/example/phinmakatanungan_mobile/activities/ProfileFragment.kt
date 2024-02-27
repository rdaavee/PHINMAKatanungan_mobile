package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.databinding.FragmentProfileBinding

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

        // initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE)

        binding.root.findViewById<LinearLayout>(R.id.ll_seventh).setOnClickListener {
            signOut()
        }

        val authToken = (requireActivity() as MainActivity).getAuthToken()

        return binding.root
    }

    private fun signOut() {
        Log.d("SignOut", "Current token before removal: " + sharedPreferences.getString("authToken", "Not Found"))
        // remove the token from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Log.d("SignOut", "Token after removal: " + sharedPreferences.getString("authToken", "Not Found"))

        // redirect back to WelcomeActivity and clear the activity stack
        val intent = Intent(requireActivity(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()  // finish MainActivity to prevent the user from navigating back
    }
}
