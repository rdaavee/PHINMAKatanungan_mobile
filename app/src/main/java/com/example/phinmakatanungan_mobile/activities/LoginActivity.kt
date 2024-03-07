package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import com.example.phinmakatanungan_mobile.models.UserData
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {

    //initialize sharedpref from PHINMAClient
    private lateinit var sharedPreferences : SharedPreferences
    private val USER_DATA_PREFS_KEY = "userData"

    //initialize client and interface

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnlogin2 = findViewById<AppCompatButton>(R.id.btnLogin2)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextemail = findViewById<EditText>(R.id.editTextEmail)

        sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE)

        PHINMAClient.setSharedPreferences(sharedPreferences)

        findViewById<TextView>(R.id.tv_signup2).setOnClickListener {
            startActivity(Intent(this@LoginActivity, ChooseRoleActivity::class.java))
        }

        btnlogin2.setOnClickListener {

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()

            if(email.isEmpty()){
                edittextemail.error = "Enter your email"
                edittextemail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                edittextpassword.error = "Enter your password"
                edittextpassword.requestFocus()
                return@setOnClickListener
            }

            val signinDataJson =
                "{\"email\":\"$email\",\"password\":\"$password\"}"

            try {
                val reader = JsonReader(StringReader(signinDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()
                PHINMAClient.instance.userLogin(
                    email,
                    password
                )
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                val tokenFromApi = response.body()?.accessToken
                                saveTokenToSharedPreferences(tokenFromApi)
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                                if(response.body()!!.message == "Invalid Credentials"){
                                    Toast.makeText(applicationContext, "Choose a branch", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val errorMessage: String = try {
                                    response.errorBody()?.string()
                                        ?: "Failed to get a valid response. Response code: ${response.code()}"
                                } catch (e: Exception) {
                                    "Failed to get a valid response. Response code: ${response.code()}"
                                }
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG)
                                    .show()
                                Log.e("API_RESPONSE", errorMessage)
                            }
                        }
                    })

            } catch (e: Exception) {
                Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }


        }
    }
    private fun saveTokenToSharedPreferences(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("authToken", token)
        editor.apply()

        if (token != null) {
            getUserInfoData(token, this)
        }
    }

    private fun getUserInfoData(token: String, loginActivity: LoginActivity) {
        PHINMAClient.instance.getUserProfile("Bearer $token").enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    try {
                        val userData = response.body()
                        if (userData != null) {
                            // Save the UserData object to SharedPreferences
                            saveUserDataToPrefs(userData, this@LoginActivity)
                            Log.d("UserDataPrefs", "User data saved to SharedPreferences: $userData")
                        } else {
                            Log.e("UserDataPrefs", "User data is null in the response body")
                        }
                    } catch (e: Exception) {
                        Log.e("UserDataPrefs", "Exception while processing user data", e)
                        Toast.makeText(applicationContext, "Failed to process user info", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("UserDataPrefs", "Failed to get user info. Response code: ${response.code()}")
                    Toast.makeText(applicationContext, "Failed to get user info", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e("UserDataPrefs", "Failed to get user info", t)
                Toast.makeText(applicationContext, "Failed to get user info", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun saveUserDataToPrefs(userData: UserData?, context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserDataPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (userData != null) {
            val userDataJson = Gson().toJson(userData)
            editor.putString(USER_DATA_PREFS_KEY, userDataJson)
            // Commit the changes
            editor.apply()
        } else {
            // Clear the UserData from SharedPreferences if null
            editor.remove(USER_DATA_PREFS_KEY)
            // Commit the changes
            editor.apply()
        }
    }

    private fun getAuthToken(): String {
        val authToken = sharedPreferences.getString("authToken", "") ?: ""
        return "Bearer $authToken"
    }

}