package com.example.phinmakatanungan_mobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_sign_up)

        val edittextemail = findViewById<EditText>(R.id.editTextEmail)
        val edittextpassword = findViewById<EditText>(R.id.editTextPassword)
        val edittextname = findViewById<EditText>(R.id.editTextName)
        val signupButton = findViewById<AppCompatButton>(R.id.buttonSignUp)
        val departmentSpinner: Spinner = findViewById(R.id.spinner_deparment)
        val departments = arrayOf("CITE", "CAHS", "CCJE", "CELA", "CEA")

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

        signupButton.setOnClickListener{

            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()
            val name = edittextname.text.toString().trim()
            val department = departmentSpinner.selectedItem.toString().trim()
            val signupDataJson = "{\"email\":\"$email\",\"password\":\"$password\",\"name\":\"$name\",\"departments\":\"$department\"}"

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

            if(department == "Choose a department"){
                Toast.makeText(applicationContext, "Choose a department", Toast.LENGTH_SHORT).show()
                departmentSpinner.requestFocus()
                return@setOnClickListener
            }

            if(name.isEmpty()){

                edittextname.error = "Name required"
                edittextname.requestFocus()
                return@setOnClickListener
            }



            PHINMAClient.instance.createTeacher(email, password, name, department)
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