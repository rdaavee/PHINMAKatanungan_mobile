package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class StudentSignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signup)

        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextname = findViewById<EditText>(R.id.editTextName)
        val edittextstudnumber = findViewById<EditText>(R.id.editTextStudNumber)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)
        val courseSpinner: Spinner = findViewById(R.id.spinner_course)
        val yearSpinner: Spinner = findViewById(R.id.spinner_year)
        val courses = arrayOf("Choose a course", "BSIT", "BSCE", "BSBA", "BSBE", "BSARCH", "BSCRIM", "BSA", "BSN")

        val courseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courses)
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        courseSpinner.adapter = courseAdapter

        courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCourse = parent?.getItemAtPosition(position).toString()

                updateYearSpinner(selectedCourse)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val name = edittextname.text.toString().trim()
            val studnumber = edittextstudnumber.text.toString().trim()
            val course = courseSpinner.selectedItem.toString().trim()
            val year = yearSpinner.selectedItem.toString().trim()
            val signupDataJson = "{\"email\":\"$email\",\"password\":\"$password\",\"name\":\"$name\",\"studnumber\":\"$studnumber\",\"course\":\"$course\",\"year\":\"$year\"}"

            if(email.isEmpty()){
                edittextemail.error = "Email required"
                edittextemail.requestFocus()
                return@setOnClickListener
            }
            if(studnumber.isEmpty()){
                edittextstudnumber.error = "Student number required"
                edittextstudnumber.requestFocus()
                return@setOnClickListener
            }

            if(name.isEmpty()){
                edittextname.error = "Name required"
                edittextname.requestFocus()
                return@setOnClickListener
            }
            if(course == "Choose a course"){
                Toast.makeText(applicationContext, "Choose a course", Toast.LENGTH_SHORT).show()
                courseSpinner.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                edittextpassword.error = "Password required"
                edittextpassword.requestFocus()
                return@setOnClickListener
            }



            try {

                val reader = JsonReader(StringReader(signupDataJson))
                reader.isLenient = true

                reader.beginObject()

                reader.close()

                PHINMAClient.instance.createUser(email, password, name, studnumber, course, year)
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
                                startActivity(Intent(this@StudentSignUpActivity,LoginActivity::class.java))
                            } else {
                                val errorMessage = "Failed to get a valid response. Response code: ${response.code()}"
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                                Log.e("API_RESPONSE", errorMessage)
                            }
                        }
                    })

            } catch (e: Exception) {
                // Handle JSON parsing error
                Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

    }
    private fun updateYearSpinner(selectedCourse: String) {

        val yearSpinner = findViewById<Spinner>(R.id.spinner_year)
        // Define years array based on selected course
        val years: Array<String> = when (selectedCourse) {
            "BSIT", "BSCE", "BSBA", "BSBE", "BSARCH", "BSCRIM", "BSA", "BSN" -> arrayOf("First Year", "Second Year", "Third Year", "Fourth Year")
            "BSARCHI"-> arrayOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")

            else -> arrayOf("Course undefined")
        }

        // Create adapter for year Spinner
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedYear = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}