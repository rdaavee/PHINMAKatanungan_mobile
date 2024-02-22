package com.example.phinmakatanungan_mobile.activities

import android.annotation.SuppressLint
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
import com.example.phinmakatanungan_mobile.dbHelper.DBHelper
import com.example.phinmakatanungan_mobile.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class LoginActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnlogin2 = findViewById<AppCompatButton>(R.id.btnLogin2)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val dbHelper = DBHelper(this@LoginActivity)

        findViewById<TextView>(R.id.tv_signup2).setOnClickListener {
            startActivity(Intent(this@LoginActivity, ChooseRoleActivity::class.java))
        }

        btnlogin2.setOnClickListener {

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()

            if(email.isEmpty()){
                edittextemail.error = "Email required"
                edittextemail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                edittextpassword.error = "Password required"
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
                                val tokenFromApi = response.body()!!.accessToken
                                val helper = DBHelper(applicationContext)
                                helper.addOrUpdateToken(tokenFromApi)
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
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
}