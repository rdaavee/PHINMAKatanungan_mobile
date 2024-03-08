package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import com.example.phinmakatanungan_mobile.adapters.PostAdapter
import com.example.phinmakatanungan_mobile.databinding.FragmentDepartmentBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DepartmentFragment : Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: FragmentDepartmentBinding
    private var courses: MutableList<String> = mutableListOf()
    private var selectedDepartment: String? = null
    private val departmentCheckboxes = mutableListOf<CheckBox>()
    private val allDepartmentViewGroups = mutableListOf<View>()
    private val departmentChipGroups = mutableListOf<ChipGroup>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentBinding.inflate(inflater, container, false)
        // Initialize postAdapter

        departmentCheckboxes.add(binding.checkboxCite)
        departmentCheckboxes.add(binding.checkboxCea)
        departmentCheckboxes.add(binding.checkboxCahs)
        departmentCheckboxes.add(binding.checkboxCcje)
        departmentCheckboxes.add(binding.checkboxCela)
        departmentCheckboxes.add(binding.checkboxCma)

        departmentChipGroups.add(binding.chipGroup1)
        departmentChipGroups.add(binding.chipGroup2)
        departmentChipGroups.add(binding.chipGroup3)
        departmentChipGroups.add(binding.chipGroup4)
        departmentChipGroups.add(binding.chipGroup5)
        departmentChipGroups.add(binding.chipGroup6)


        //cite
        binding.chipBsit.setOnClickListener {
            val department = "CITE"
            val course = "BSIT"
            toggleChipSelection(binding.chipBsit,course,department)
        }
        binding.checkboxCite.setOnClickListener{
            val department = "CITE"
            toggleDepartmentSelection(binding.checkboxCite,department,binding.chipGroup1)
        }
        //cea
        binding.checkboxCea.setOnClickListener{
            val department = "CEA"
            toggleDepartmentSelection(binding.checkboxCea,department,binding.chipGroup2)
        }
        binding.chipBsce.setOnClickListener {
            val department = "CEA"
            val course = "BSCE"
            toggleChipSelection(binding.chipBsce,course,department)
        }
        binding.chipBsee.setOnClickListener {
            val department = "CEA"
            val course = "BSEE"
            toggleChipSelection(binding.chipBsee,course,department)
        }
        binding.chipBsarch.setOnClickListener {
            val department = "CEA"
            val course = "BSArch"
            toggleChipSelection(binding.chipBsarch,course,department)
        }
        binding.chipBscpe.setOnClickListener {
            val department = "CEA"
            val course = "BSCpE"
            toggleChipSelection(binding.chipBscpe,course,department)
        }
        binding.chipBsme.setOnClickListener {
            val department = "CEA"
            val course = "BSME"
            toggleChipSelection(binding.chipBsme,course,department)
        }
        //cahs
        binding.checkboxCahs.setOnClickListener{
            val department = "CAHS"
            toggleDepartmentSelection(binding.checkboxCahs,department,binding.chipGroup3)
        }
        binding.chipBsn.setOnClickListener {
            val department = "CAHS"
            val course = "BSN"
            toggleChipSelection(binding.chipBsn,course,department)
        }
        binding.chipBspharm.setOnClickListener {
            val department = "CAHS"
            val course = "BSPharm"
            toggleChipSelection(binding.chipBspharm,course,department)
        }
        binding.chipBmls.setOnClickListener {
            val department = "CAHS"
            val course = "BMLS"
            toggleChipSelection(binding.chipBmls,course,department)
        }
        binding.chipBspsych.setOnClickListener {
            val department = "CAHS"
            val course = "BSPsych"
            toggleChipSelection(binding.chipBspsych,course,department)
        }
        //CCJE
        binding.checkboxCcje.setOnClickListener{
            val department = "CCJE"
            toggleDepartmentSelection(binding.checkboxCcje,department,binding.chipGroup4)
        }
        binding.chipBscrim.setOnClickListener {
            val department = "CCJE"
            val course = "BSCrim"
            toggleChipSelection(binding.chipBscrim,course,department)
        }
        //cela
        binding.checkboxCela.setOnClickListener{
            val department = "CELA"
            toggleDepartmentSelection(binding.checkboxCela,department,binding.chipGroup5)
        }
        binding.chipAbcomm.setOnClickListener {
            val department = "CELA"
            val course = "ABComm"
            toggleChipSelection(binding.chipAbcomm,course,department)
        }
        binding.chipAbpolsci.setOnClickListener {
            val department = "CELA"
            val course = "ABPolSci"
            toggleChipSelection(binding.chipAbpolsci,course,department)
        }
        binding.chipBseduc.setOnClickListener {
            val department = "CELA"
            val course = "BSEduc"
            toggleChipSelection(binding.chipBseduc,course,department)
        }
        binding.chipBsed.setOnClickListener {
            val department = "CELA"
            val course = "BSED"
            toggleChipSelection(binding.chipBsed,course,department)
        }
        //CMA
        binding.checkboxCma.setOnClickListener{
            val department = "CMA"
            toggleDepartmentSelection(binding.checkboxCma,department,binding.chipGroup6)
        }
        binding.chipBsa.setOnClickListener {
            val department = "CMA"
            val course = "BSA"
            toggleChipSelection(binding.chipBsa,course,department)
        }
        binding.chipBsma.setOnClickListener {
            val department = "CMA"
            val course = "BSMA"
            toggleChipSelection(binding.chipBsma,course,department)
        }
        binding.chipBsat.setOnClickListener {
            val department = "CMA"
            val course = "BSAT"
            toggleChipSelection(binding.chipBsat,course,department)
        }
        binding.chipBshm.setOnClickListener {
            val department = "CMA"
            val course = "BSHM"
            toggleChipSelection(binding.chipBshm,course,department)
        }
        binding.chipBstm.setOnClickListener {
            val department = "CMA"
            val course = "BSTM"
            toggleChipSelection(binding.chipBstm,course,department)
        }
        binding.chipBsba.setOnClickListener {
            val department = "CMA"
            val course = "BSBA"
            toggleChipSelection(binding.chipBsba,course,department)
        }

        postAdapter = PostAdapter()

        return binding.root
    }

    private fun disableChipgroup1(){
        binding.chipBsit.isChecked = false
    }
    private fun disableChipgroup2(){
        binding.chipBsce.isChecked = false
        binding.chipBsee.isChecked = false
        binding.chipBsarch.isChecked = false
        binding.chipBscpe.isChecked = false
        binding.chipBsme.isChecked = false
    }
    private fun disableChipgroup3(){
        binding.chipBsn.isChecked = false
        binding.chipBspharm.isChecked = false
        binding.chipBspsych.isChecked = false
        binding.chipBmls.isChecked = false
    }

    private fun disableChipgroup4(){
        binding.chipBscrim.isChecked = false

    }
    private fun disableChipgroup5(){
        binding.chipAbcomm.isChecked = false
        binding.chipAbpolsci.isChecked = false
        binding.chipBseduc.isChecked = false
        binding.chipBsed.isChecked = false
    }
    private fun disableChipgroup6(){
        binding.chipBsa.isChecked = false
        binding.chipBsma.isChecked = false
        binding.chipBsat.isChecked = false
        binding.chipBstm.isChecked = false
        binding.chipBsba.isChecked = false
    }

    private fun toggleChipSelection(chip: Chip, course: String, department: String) {
        val isChecked = chip.isChecked
        when (chip.text.toString()) {
            "BSIT" -> {
                // Run function specific to chipGroup2
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
            }
            "BSME", "BSCE", "BSCE","BSArch","BSCpE" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
            }
            "BSN", "BSPharm", "BSPsych","BMLS" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
            }
            "BSCrim", "BSPharm", "BSPsych","BMLS" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup5()
                disableChipgroup6()
            }
            "ABComm","ABPolSci","BSEduc","BSEduc","BSED" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup6()
            }
            "BSA","BSMA","BSAT","BSHM","BSTM","BSBA" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
            }
            else -> {
                // Handle the case where the text doesn't match any known chip group
            }
        }
        if (isChecked) {
            if (!courses.contains(course)) {
                courses.add(course)
            }
        } else {
            courses.remove(course)
        }
        setData(department, courses)
        // Check if courses is empty, if so, remove the department from preferences
        if (courses.isEmpty()) {
            removeDepartment(department)
        }
    }

    private fun toggleDepartmentSelection(checkbox: CheckBox, department: String, chipGroup: ChipGroup) {
        val isChecked = checkbox.isChecked

        // Uncheck all other checkboxes if the clicked checkbox is checked
        if (isChecked) {
            for (otherCheckbox in departmentCheckboxes) {
                if (otherCheckbox != checkbox) {
                    otherCheckbox.isChecked = false
                }
            }
            // If a department is already selected, uncheck it first
            selectedDepartment?.let { previousDepartment ->
                if (previousDepartment != department) {
                    // If the clicked department is different from the previously selected one, uncheck the previous one
                    val previousCheckbox = departmentCheckboxes.find { it.text == previousDepartment }
                    previousCheckbox?.isChecked = false
                }
            }
            // Set the currently selected department
            selectedDepartment = department

            // Set all chips in the specified chip group to checked state
            val chipGroupChips = chipGroup.children.filterIsInstance<Chip>()
            chipGroupChips.forEach { chip ->
                chip.isChecked = true
            }

            // Make other chip groups unclickable
            departmentChipGroups.forEach { group ->
                if (group != chipGroup) {
                    group.children.forEach { chip ->
                        if (chip is Chip) {
                            chip.isClickable = false
                            chip.isChecked = false
                        }
                    }
                }
            }
        } else {
            // Clear the selected department
            selectedDepartment = null

            // Uncheck all chips in the specified chip group
            chipGroup.children.filterIsInstance<Chip>().forEach { chip ->
                chip.isChecked = false
                courses.clear()
            }

            // Make all chips in all chip groups clickable again
            departmentChipGroups.forEach { group ->
                group.isClickable = true
                group.children.filterIsInstance<Chip>().forEach { chip ->
                    chip.isClickable = true
                }
            }
        }

        // Send only the department to the setData function
        setData(selectedDepartment ?: "", emptyList())
    }



    private fun setData(dept: String, courses: List<String>) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val savedDepartment = sharedPreferences.getString("department", null)

        // Check if the department already exists in the filter preferences
        if (savedDepartment != dept) {
            val editor = sharedPreferences.edit()
            editor.putString("department", dept)
            editor.putStringSet("courses", courses.toSet())
            editor.apply()
            Log.d("DepartmentFragment", "Setting data - Department: $dept, Courses: $courses")
        } else {
            // Department already exists in the filter preferences, no need to insert again
            Log.d("DepartmentFragment", "Department $dept already exists in filter, not inserting again")
        }

        // Update or remove the courses in the filter preferences
        val editor = sharedPreferences.edit()
        if (courses.isEmpty()) {
            // If the course list is empty, remove values under the "courses" category
            editor.putStringSet("courses", null)
            Log.d("DepartmentFragment", "Removed courses from filter preferences")
        } else {
            // If there are courses, update the values under the "courses" category
            editor.putStringSet("courses", courses.toSet())
            Log.d("DepartmentFragment", "Updated courses in filter preferences: $courses")
        }
        editor.apply()
    }

    private fun removeDepartment(department: String) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("department")
        editor.apply()
        Log.d("removeDepartment", "Removed department: $department from filter preferences")
    }
}
