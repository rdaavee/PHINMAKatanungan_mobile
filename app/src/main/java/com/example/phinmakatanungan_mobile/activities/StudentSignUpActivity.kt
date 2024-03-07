package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.api.PHINMAApi
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


        //variables
        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextfirstname = findViewById<EditText>(R.id.editTextfirstName)
        val edittextmiddlename = findViewById<EditText>(R.id.editTextMiddleName)
        val edittextlastname = findViewById<EditText>(R.id.editTextLastName)
        val edittextstudnumber = findViewById<EditText>(R.id.editTextStudNumber)
        val edittextconfirmpassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)
        val departmentSpinner : Spinner = (findViewById(R.id.spinner_department))
        val schoolSpinner: Spinner = findViewById(R.id.spinner_school)
        val courseSpinner: Spinner = findViewById(R.id.spinner_course)
        val genderSpinner: Spinner = findViewById(R.id.spinner_gender)
        val yearSpinner: Spinner = findViewById(R.id.spinner_year)
        val schools = arrayOf("Select branch", "University of Pangasinan", "Araullo University", "Cagayan De Oro College", "University of ILOILO")
        val departments = arrayOf("Select department","SHS","CITE","CEA","CAHS","CCJE","CELA","CMA")
        val genders = arrayOf("Define gender","Male", "Female")

        //adapters for the spinners
        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departments)
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentSpinner.adapter = departmentAdapter

        departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDepartment = parent?.getItemAtPosition(position).toString()
                updateCourseSpinner(selectedDepartment)
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

        findViewById<ImageView>(R.id.iv_backBtnChooseRole).setOnClickListener {
            startActivity(Intent(this@StudentSignUpActivity, ChooseRoleActivity::class.java))
        }

        //signup functionality
        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val confirmPassword = edittextconfirmpassword.text.toString().trim()
            val firstName = edittextfirstname.text.toString().trim()
            val middleName = edittextmiddlename.text.toString().trim()
            val lastName = edittextlastname.text.toString().trim()
            val selectedGender = genderSpinner.selectedItem.toString().trim()
            val userID = edittextstudnumber.text.toString().trim()
            val departmentID = departmentSpinner.selectedItem.toString().trim()
            val school = schoolSpinner.selectedItem.toString().trim()
            val course = courseSpinner.selectedItem.toString().trim()
            val year = yearSpinner.selectedItem.toString().trim()
            val role = "Student"
            var campus = ""

            when(school){
                "University of Pangasinan" -> campus = "01"
                "Araullo University" -> campus = "02"
                "Cagayan De Oro College" -> campus = "03"
                "University of ILOILO" -> campus = "04"
                else -> campus = "Select branch"
            }
            //json data
            val signupDataJson = "{\"user_id\":\"$userID\",\"user_role\":\"$role\",\"first_name\":\"$firstName\",\"middle_name\":\"$middleName\",\"last_name\":\"$lastName\",\"gender\":\"$selectedGender\",\"email\":\"$email\",\"password\":\"$password\",\"year_level\":\"$year\",\"course_id\":\"$course\",\"department_id\":\"$departmentID\",\"school_id\":\"$campus\"}"

            //validation
            when {
                email.isEmpty() -> {
                    edittextemail.error = "Email required"
                    edittextemail.requestFocus()
                }
                userID.isEmpty() -> {
                    edittextstudnumber.error = "Student number required"
                    edittextstudnumber.requestFocus()
                }
                firstName.isEmpty() -> {
                    edittextfirstname.error = "Name required"
                    edittextfirstname.requestFocus()
                }
                middleName.isEmpty() -> {
                    edittextmiddlename.error = "Name required"
                    edittextmiddlename.requestFocus()
                }
                lastName.isEmpty() -> {
                    edittextlastname.error = "Name required"
                    edittextlastname.requestFocus()
                }
                selectedGender == "Select gender" -> {
                    edittextlastname.error = "Gender required"
                    edittextlastname.requestFocus()
                }
                campus == "Select branch" -> {
                    Toast.makeText(applicationContext, "Choose a branch", Toast.LENGTH_SHORT).show()
                    courseSpinner.requestFocus()
                }
                departmentID == "Select department" -> {
                    Toast.makeText(applicationContext, "Choose a department", Toast.LENGTH_SHORT).show()
                    courseSpinner.requestFocus()
                }
                course == "Choose a course" -> {
                    Toast.makeText(applicationContext, "Choose a course", Toast.LENGTH_SHORT).show()
                    courseSpinner.requestFocus()
                }
                password.isEmpty() -> {
                    edittextpassword.error = "Password required"
                    edittextpassword.requestFocus()
                }
                password != confirmPassword -> {
                    edittextconfirmpassword.error = "Password doesn't match"
                    edittextconfirmpassword.requestFocus()
                }
                else -> {
                    // All validations passed, proceed with registration or other action
                    // For example: performRegistration()
                }
            }

            //correct malformed data
            try {
                val reader = JsonReader(StringReader(signupDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()
                PHINMAClient.instance.createUser(
                    userID,
                    role,
                    firstName,
                    middleName,
                    lastName,
                    selectedGender,
                    email,
                    password,
                    year,
                    course,
                    departmentID,
                    campus)
                    .enqueue(object : Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            if (response.isSuccessful && response.body() != null) {
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@StudentSignUpActivity, LoginActivity::class.java))
                                finish()
                            } else {
                                val errorMessage: String = try {
                                    response.errorBody()?.string() ?: "Failed to get a valid response. Response code: ${response.code()}"
                                } catch (e: Exception) {
                                    "Failed to get a valid response. Response code: ${response.code()}"
                                }
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
    private fun updateCourseSpinner(selectedDepartment: String) {

        val courseSpinner = findViewById<Spinner>(R.id.spinner_course)
        // Define courses array based on selected department
        val courses: Array<String> = when (selectedDepartment) {
            "CITE"  -> arrayOf("BSIT")
            "CEA" -> arrayOf("BSCE","BSEE","BSArch","BSCpE","BSME")
            "CAHS" -> arrayOf("BSN","BSPharm","BMLS","BSPsych")
            "CCJE" -> arrayOf("BSCrim")
            "CELA" -> arrayOf("ABComm","ABPolSci","BSEEduc","BSED")
            "CMA" -> arrayOf("BSA","BSMA","BSAT","BSHM","BSTM","BSBA")
            "SHS" -> arrayOf("HUMMS","STEM","GAS","ABM")
            else -> arrayOf("Department Undefined")
        }

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
    }

    //updates spinner based on course
    private fun updateYearSpinner(selectedCourse: String) {

        val yearSpinner = findViewById<Spinner>(R.id.spinner_year)
        // Define years array based on selected course
        val years: Array<String> = when (selectedCourse) {
            "BSCE","BSEE","BSCpE","BSME","BSN","BSPharm","BMLS","BSPsych",
            "BSCrim","ABComm","ABPolSci","BSEEduc","BSED",
            "BSA","BSMA","BSAT","BSHM","BSTM","BSBA","BSIT"-> arrayOf("First", "Second", "Third", "Fourth")
            "BSArch"-> arrayOf("First", "Second", "Third", "Fourth", "Fifth")
            "HUMMS", "STEM", "GAS", "ABM" -> arrayOf("Grade 11","Grade 12")

            else -> arrayOf("Course undefined")
        }

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

    override fun finish() {
        super.finish()
        // Animation when the activity is finished (exited)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}