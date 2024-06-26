package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAApi
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class TeacherSignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_sign_up)

        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextfirstname = findViewById<EditText>(R.id.editTextfirstName)
        val edittextmiddlename = findViewById<EditText>(R.id.editTextMiddleName)
        val edittextlastname = findViewById<EditText>(R.id.editTextLastName)
        val edittextidnumber = findViewById<EditText>(R.id.editTextIDNumber)
        val edittextconfirmpassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)
        val departmentSpinner: Spinner = findViewById(R.id.spinner_department)
        val schoolSpinner: Spinner = findViewById(R.id.spinner_school)
        val genderSpinner: Spinner = findViewById(R.id.spinner_gender)
        val departments = arrayOf("Select department","SHS","CITE","CEA","CAHS","CCJE","CELA","CMA")
        val schools = arrayOf("Select branch", "University of Pangasinan", "Araullo University", "Cagayan De Oro College", "University of ILOILO")
        val genders = arrayOf("Define gender","Male", "Female")

        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departments)
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = departmentAdapter

        departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDepartment = parent?.getItemAtPosition(position).toString()
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

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGender = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        findViewById<ImageView>(R.id.iv_backBtnChooseRole2).setOnClickListener {
            startActivity(Intent(this@TeacherSignUpActivity, ChooseRoleActivity::class.java))
        }

        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val confirmPassword = edittextconfirmpassword.text.toString().trim()
            val firstName = edittextfirstname.text.toString().trim()
            val middleName = edittextmiddlename.text.toString().trim()
            val lastName = edittextlastname.text.toString().trim()
            val userID = edittextidnumber.text.toString().trim()
            val selectedGender = genderSpinner.selectedItem.toString().trim() //haha dagdagan lang pala ng "selectedItem"
            val school = schoolSpinner.selectedItem.toString().trim()
            val departmentID = departmentSpinner.selectedItem.toString().trim()
            val courseID = departmentSpinner.selectedItem.toString().trim()
            val role = "Teacher"
            var campus = ""

            when(school){
                "University of Pangasinan" -> campus = "01"
                "Araullo University" -> campus = "02"
                "Cagayan De Oro College" -> campus = "03"
                "University of ILOILO" -> campus = "04"
                else -> campus = "Select branch"
            }

            val signupDataJson = "{\"user_id\":\"$userID\",\"user_role\":\"$role\",\"first_name\":\"$firstName\",\"middle_name\":\"$middleName\",\"last_name\":\"$lastName\",\"gender\":\"$selectedGender\",\"email\":\"$email\",\"password\":\"$password\",\"course_id\":\"$courseID\",\"department_id\":\"$departmentID\",\"school_id\":\"$campus\"}"

            if(email.isEmpty()){

                edittextemail.error = "Email required"
                edittextemail.requestFocus()
                return@setOnClickListener
            }

            if(userID.isEmpty()){
                edittextidnumber.error = "ID required"
                edittextidnumber.requestFocus()
                return@setOnClickListener
            }

            if(firstName.isEmpty()){
                edittextfirstname.error = "First name required"
                edittextfirstname.requestFocus()
                return@setOnClickListener
            }
            if(middleName.isEmpty()){
                edittextmiddlename.error = "Middle name required"
                edittextmiddlename.requestFocus()
                return@setOnClickListener
            }
            if(lastName.isEmpty()){
                edittextlastname.error = "Last name required"
                edittextlastname.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){

                edittextpassword.error = "Password required"
                edittextpassword.requestFocus()
                return@setOnClickListener
            }
            if(password != confirmPassword){
                edittextconfirmpassword.error = "Password doesn't match"
                edittextconfirmpassword.requestFocus()
                return@setOnClickListener
            }
            try {

                val reader = JsonReader(StringReader(signupDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()

                PHINMAClient.instance.createTeacher(
                    userID,
                    role,
                    firstName,
                    middleName,
                    lastName,
                    selectedGender,
                    email,
                    password,
                    courseID,
                    departmentID,
                    campus)
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
                                startActivity(Intent(this@TeacherSignUpActivity,LoginActivity::class.java))
                                finish()
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

}