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
        val edittextfirstname = findViewById<EditText>(R.id.editTextfirstName)
        val edittextmiddlename = findViewById<EditText>(R.id.editTextMiddleName)
        val edittextlastname = findViewById<EditText>(R.id.editTextLastName)
        val edittextstudnumber = findViewById<EditText>(R.id.editTextStudNumber)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)
        val schoolSpinner: Spinner = findViewById(R.id.spinner_school)
        val courseSpinner: Spinner = findViewById(R.id.spinner_course)
        val yearSpinner: Spinner = findViewById(R.id.spinner_year)
        val schools = arrayOf("UPang - Dagupan", "UPang - Urdaneta") //eto Dagpuan to dati sabe ni dabid
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

        val schoolAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schools)
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        schoolSpinner.adapter = schoolAdapter

        schoolSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSchool = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val firstName = edittextfirstname.text.toString().trim()
            val middleName = edittextmiddlename.text.toString().trim()
            val lastName = edittextlastname.text.toString().trim()
            val studentID = edittextstudnumber.text.toString().trim()
            val school = schoolSpinner.selectedItem.toString().trim()
            val course = courseSpinner.selectedItem.toString().trim()
            val year = yearSpinner.selectedItem.toString().trim()
            var camp = ""
            if(school == "UPang - Dagupan") { ///Dagpuan to dati AHAHAAHAHAAHA
                camp = "01"
            }
            else if(school == "UPang - Urdaneta") {
                camp = "02"
            }

            val signupDataJson = "{\"student_id\":\"$studentID\",\"first_name\":\"$firstName\",\"middle_name\":\"$middleName\",\"last_name\":\"$lastName\",\"email\":\"$email\",\"password\":\"$password\",\"year_level\":\"$year\",\"course_id\":\"$course\",\"school_id\":\"$school\"}"


            if(email.isEmpty()){
                edittextemail.error = "Email required"
                edittextemail.requestFocus()
                return@setOnClickListener
            }
            if(studentID.isEmpty()){
                edittextstudnumber.error = "Student number required"
                edittextstudnumber.requestFocus()
                return@setOnClickListener
            }

            if(firstName.isEmpty()){
                edittextfirstname.error = "Name required"
                edittextfirstname.requestFocus()
                return@setOnClickListener
            }
            if(middleName.isEmpty()){
                edittextmiddlename.error = "Name required"
                edittextmiddlename.requestFocus()
                return@setOnClickListener
            }
            if(lastName.isEmpty()){
            edittextlastname.error = "Name required"
            edittextlastname.requestFocus()
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
                PHINMAClient.instance.createUser(studentID,firstName,middleName,lastName,email,password,year,course,camp)
                    .enqueue(object : Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            if (response.isSuccessful && response.body() != null) {
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@StudentSignUpActivity, LoginActivity::class.java))
                            } else {
                                val errorMessage = "Failed to get a valid response. Response code: ${response.code()}"
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
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
    private fun updateYearSpinner(selectedCourse: String) {

        val yearSpinner = findViewById<Spinner>(R.id.spinner_year)
        // Define years array based on selected course
        val years: Array<String> = when (selectedCourse) {
            "BSIT", "BSCE", "BSBA", "BSBE", "BSCRIM", "BSA", "BSN" -> arrayOf("First", "Second", "Third", "Fourth")
            "BSARCH"-> arrayOf("First", "Second", "Third", "Fourth", "Fifth")

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