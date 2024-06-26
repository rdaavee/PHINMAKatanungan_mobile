package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.databinding.FragmentCreatePostBinding
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class CreatePostFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding
    private var privacy = "public"
    private lateinit var sharedPreferences: SharedPreferences
    private val userDataPrefKeys = "userData"
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        val sharedPreferences = context?.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences?.getString(userDataPrefKeys, null)

        if (userDataJson != null) {
            val userData = Gson().fromJson(userDataJson, UserData::class.java)
            context?.let { updateInfo(userData, it) }
        } else {
            Log.e("UserInfoUpdate", "User data not found in SharedPreferences")
        }
        binding.ivBackBtnCreate.setOnClickListener{
            navigateToDashboardFragment()
        }
        binding.tvPostBtn.setOnClickListener {
            val title = binding.etPostTitle.text.toString().trim()
            val content = binding.etPostDescription.text.toString().trim()
            val sharedPreferences = requireContext().getSharedPreferences("myPreference", Context.MODE_PRIVATE)
            val authToken = sharedPreferences.getString("authToken", "") ?: ""

            val signupDataJson = "{\"api_token\":\"$authToken\",\"title\":\"$title\",\"content\":\"$content\",\"privacy\":\"$privacy\"}"

            if(title.isEmpty()){
                binding.etPostTitle.error = "Enter a title"
            }
            if(content.isEmpty()){
                binding.etPostDescription.error = "Enter description"
            }
            try {
                val reader = JsonReader(StringReader(signupDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()
                PHINMAClient.instance.createPost(
                    authToken,
                    title,
                    content,
                    privacy
                ).enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val message = response.body()!!.message
                            if (message == "Post successfully created.") {
                                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                                alertDialogBuilder.setTitle("Successfully posted")
                                alertDialogBuilder.setMessage(message)
                                alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                    navigateToDashboardFragment()
                                }
                                val alertDialog = alertDialogBuilder.create()
                                alertDialog.show()
                            }
                            binding.etPostTitle.text = null
                            binding.etPostDescription.text = null
                        } else {
                            val errorMessage: String = try {
                                response.errorBody()?.string() ?: "Failed to get a valid response. Response code: ${response.code()}"
                            } catch (e: Exception) {
                                "Failed to get a valid response. Response code: ${response.code()}"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("API_RESPONSE", errorMessage)
                        }
                    }
                })
            } catch (e: Exception) {
                Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        return binding.root
    }
    private fun navigateToDashboardFragment() {
        val navController = findNavController()
        navController.popBackStack(R.id.dashboardFragment, false)
    }
    fun updateInfo(userData: UserData, context: Context) {
        val fullName = "${userData.first_name} ${userData.middle_name} ${userData.last_name}"
        binding.tvFullName.text = fullName
    }
}