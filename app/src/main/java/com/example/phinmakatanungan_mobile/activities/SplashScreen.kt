package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: SharedPrefsViewModel

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        progressBar = findViewById(R.id.progressBar)

        installSplashScreen()


        // Initialize SharedPreferences and ViewModel
        sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(SharedPrefsViewModel::class.java)

        // Check if it's the first time the app is launched
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        if (isFirstTime) {
            // If it's the first time, show the WelcomeActivity and set the flag to false
            redirectToWelcomeActivity()
            sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
        } else {
            // If it's not the first time, proceed with token verification
            proceedWithTokenVerification()
        }
    }

    private fun proceedWithTokenVerification() {
        val authToken = sharedPreferences.getString("authToken", "")
        if (authToken?.isNotEmpty() == true) {
            initializeAuthToken(authToken)
        } else {
            // If there is no authToken, redirect to WelcomeActivity
            redirectToWelcomeActivity()
        }
    }


    private fun redirectToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun initializeAuthToken(authToken: String) {
        PHINMAClient.setSharedPreferences(sharedPreferences) // Set SharedPreferences before using it
        PHINMAClient.instance.verifyToken("Bearer $authToken")
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if (response.isSuccessful) {
                        viewModel.authToken = "Bearer $authToken"

                        progressBar.visibility = View.GONE

                        redirectToMainActivity()

                    } else {
                        Log.e("PHINMAKatanungan", "Token verification failed: ${response.message()}")
                        Toast.makeText(applicationContext, "Token verification failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                        try {
                            val filterPrefs = applicationContext.getSharedPreferences("filter", Context.MODE_PRIVATE)
                            filterPrefs.edit().clear().apply()
                            val userDataPrefs = applicationContext.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
                            userDataPrefs.edit().clear().apply()
                            sharedPreferences.edit().remove("authToken").apply()

                            progressBar.visibility = View.GONE

                            redirectToLoginActivity()

                        } catch (e: Exception) {
                            Log.e("PHINMAKatanungan", "Failed to remove authToken from SharedPreferences", e)
                        }
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    // Failure in making the network request
                    Log.e("PHINMAKatanungan", "Failed to verify token", t)
                    Toast.makeText(applicationContext, "Failed to verify token", Toast.LENGTH_SHORT).show()

                    progressBar.visibility = View.GONE

                    redirectToLoginActivity()
                }
            })

        android.os.Handler().postDelayed({
            // don't remove this
        }, 8000)


    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
