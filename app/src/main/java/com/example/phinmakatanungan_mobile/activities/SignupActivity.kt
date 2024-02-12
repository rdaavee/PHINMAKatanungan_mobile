package com.example.phinmakatanungan_mobile.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextname = findViewById<EditText>(R.id.editTextName)
        val edittextstudnumber = findViewById<EditText>(R.id.editTextStudNumber)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)

        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val name = edittextname.text.toString().trim()
            val studnumber = edittextstudnumber.text.toString().trim()

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

            if(name.isEmpty()){

                edittextname.error = "Name required"
                edittextname.requestFocus()
                return@setOnClickListener
            }

            if(studnumber.isEmpty()){

                edittextstudnumber.error = "Student number required"
                edittextstudnumber.requestFocus()
                return@setOnClickListener
            }

            PHINMAClient.instance.createUser(email, password, name, studnumber)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                        } else {
                            val errorMessage = "Failed to get a valid response. Response code: ${response.code()}"
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("API_RESPONSE", errorMessage)
                        }
                    }
                })

        }

    }
}