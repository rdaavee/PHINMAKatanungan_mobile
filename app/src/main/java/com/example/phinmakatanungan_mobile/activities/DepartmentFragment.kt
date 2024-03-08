package com.example.phinmakatanungan_mobile.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.databinding.FragmentDepartmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DepartmentFragment : Fragment() {

    private lateinit var binding: FragmentDepartmentBinding
    private var courses: MutableList<String> = mutableListOf()
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavHidden = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentBinding.inflate(inflater, container, false)
        // Initialize postAdapter
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)

        var lastScrollY = 0
        view?.findViewById<NestedScrollView>(R.id.nestedScrollView)
            ?.setOnScrollChangeListener { _, _, _, _, scrollY ->
            if (scrollY > lastScrollY && !isBottomNavHidden) {
                // Scrolling down, hide bottom nav
                hideBottomNav()
            } else if (scrollY <= lastScrollY && isBottomNavHidden) {
                // Scrolling up, show bottom nav
                showBottomNav()
            }
            lastScrollY = scrollY
        }

        //cite
        binding.chipBsit.setOnClickListener {
            val department = "CITE"
            val course = "BSIT"
            toggleChipSelection(binding.chipBsit,course,department)
        }
        binding.chipCite.setOnClickListener {
            val department = "CITE"
            val course = "CITE"
            toggleChipSelection(binding.chipCite,course,department)
        }
        //cea
        binding.chipCea.setOnClickListener {
            val department = "CEA"
            val course = "CEA"
            toggleChipSelection(binding.chipCea,course,department)
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
        binding.chipCahs.setOnClickListener {
            val department = "CAHS"
            val course = "CAHS"
            toggleChipSelection(binding.chipCahs,course,department)
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

        binding.chipCcje.setOnClickListener {
            val department = "CCJE"
            val course = "CCJE"
            toggleChipSelection(binding.chipCcje,course,department)
        }
        binding.chipBscrim.setOnClickListener {
            val department = "CCJE"
            val course = "BSCrim"
            toggleChipSelection(binding.chipBscrim,course,department)
        }
        //cela
        binding.chipCela.setOnClickListener {
            val department = "CELA"
            val course = "CELA"
            toggleChipSelection(binding.chipCela,course,department)
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
            val course = "BSEEduc"
            toggleChipSelection(binding.chipBseduc,course,department)
        }
        binding.chipBsed.setOnClickListener {
            val department = "CELA"
            val course = "BSED"
            toggleChipSelection(binding.chipBsed,course,department)
        }
        //CMA

        binding.chipCma.setOnClickListener {
            val department = "CMA"
            val course = "CMA"
            toggleChipSelection(binding.chipCma,course,department)
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

        //SHS
        binding.chipShs.setOnClickListener {
            val department = "SHS"
            val course = "SHS"
            toggleChipSelection(binding.chipShs,course,department)
        }

        binding.chipStem.setOnClickListener {
            val department = "SHS"
            val course = "STEM"
            toggleChipSelection(binding.chipStem,course,department)
        }

        binding.chipAbm.setOnClickListener {
            val department = "SHS"
            val course = "ABM"
            toggleChipSelection(binding.chipAbm,course,department)
        }

        binding.chipHumss.setOnClickListener {
            val department = "SHS"
            val course = "HUMSS"
            toggleChipSelection(binding.chipHumss,course,department)
        }

        binding.chipGas.setOnClickListener {
            val department = "SHS"
            val course = "GAS"
            toggleChipSelection(binding.chipGas,course,department)
        }

        binding.chipTvl.setOnClickListener {
            val department = "SHS"
            val course = "TVL"
            toggleChipSelection(binding.chipTvl,course,department)
        }

        return binding.root
    }

    private fun toggleChipSelection(chip: Chip, course: String, department: String) {
        val isChecked = chip.isChecked

        if (isChecked) {
            // If the chip is checked, add the course to the list
            if (!courses.contains(course)) {
                courses.add(course)
                // Add the course directly to SharedPreferences
                addCourseToSharedPreferences(course)
            }
        } else {
            // If the chip is unchecked, remove the course from the list
            if (courses.contains(course)) {
                courses.remove(course)
                // Remove the course directly from SharedPreferences
                removeCourseFromSharedPreferences(course)
            }
        }
        when (chip.text.toString()) {
            "BSIT", "CITE Instructors" -> {
                // Run function specific to chipGroup2
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
                disableChipgroup7()
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEduc")
                courses.remove("BSED")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "BSME", "BSCE", "BSArch", "BSCpE", "BSEE", "CEA Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
                disableChipgroup7()
                courses.remove("BSIT")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEEduc")
                courses.remove("BSED")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "BSN", "BSPharm", "BSPsych", "BMLS", "CAHS Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup6()
                disableChipgroup7()
                courses.remove("BSIT")
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEEduc")
                courses.remove("BSED")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "BSCrim", "CCJE Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup5()
                disableChipgroup6()
                disableChipgroup7()
                courses.remove("BSIT")
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEEduc")
                courses.remove("BSED")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "ABComm", "ABPolSci", "BSEEduc", "BSED", "CELA Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup6()
                disableChipgroup7()
                courses.remove("BSIT")
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "BSA", "BSMA", "BSAT", "BSHM", "BSTM", "BSBA", "CMA Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup5()
                disableChipgroup7()
                courses.remove("BSIT")
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEEduc")
                courses.remove("BSED")
                courses.remove("TVL")
                courses.remove("STEM")
                courses.remove("ABM")
                courses.remove("HUMSS")
                courses.remove("GAS")
            }

            "STEM", "GAS", "TVL", "HUMSS", "ABM", "SHS Instructors" -> {
                // Run function specific to chipGroup1
                disableChipgroup1()
                disableChipgroup2()
                disableChipgroup3()
                disableChipgroup4()
                disableChipgroup6()
                courses.remove("BSIT")
                courses.remove("BSME")
                courses.remove("BSCE")
                courses.remove("BSArch")
                courses.remove("BSEE")
                courses.remove("BSCpE")
                courses.remove("BSN")
                courses.remove("BSPharm")
                courses.remove("BSPsych")
                courses.remove("BMLS")
                courses.remove("ABComm")
                courses.remove("ABPolSci")
                courses.remove("BSEEduc")
                courses.remove("BSED")
                courses.remove("BSA")
                courses.remove("BSMA")
                courses.remove("BSAT")
                courses.remove("BSHM")
                courses.remove("BSTM")
                courses.remove("BSBA")
            }
        }
        if (courses.isEmpty()) {
            removeDepartmentFromSharedPreferences()
        } else {
            // Update the department directly in SharedPreferences
            updateDepartmentInSharedPreferences(department)
        }
    }
    private fun disableChipgroup1(){
        binding.chipBsit.isChecked = false
        binding.chipCite.isChecked =  false
    }
    private fun disableChipgroup2(){
        binding.chipBsce.isChecked = false
        binding.chipBsee.isChecked = false
        binding.chipBsarch.isChecked = false
        binding.chipBscpe.isChecked = false
        binding.chipBsme.isChecked = false
        binding.chipCea.isChecked =false
    }
    private fun disableChipgroup3(){
        binding.chipBsn.isChecked = false
        binding.chipBspharm.isChecked = false
        binding.chipBspsych.isChecked = false
        binding.chipBmls.isChecked = false
        binding.chipCahs.isChecked =false
    }

    private fun disableChipgroup4(){
        binding.chipBscrim.isChecked = false
        binding.chipCcje.isChecked = false

    }
    private fun disableChipgroup5(){
        binding.chipAbcomm.isChecked = false
        binding.chipAbpolsci.isChecked = false
        binding.chipBseduc.isChecked = false
        binding.chipBsed.isChecked = false
        binding.chipCela.isChecked =false
    }
    private fun disableChipgroup6(){
        binding.chipBsa.isChecked = false
        binding.chipBsma.isChecked = false
        binding.chipBsat.isChecked = false
        binding.chipBstm.isChecked = false
        binding.chipBsba.isChecked = false
        binding.chipCma.isChecked =false
    }

    private fun disableChipgroup7(){
        binding.chipStem.isChecked = false
        binding.chipGas.isChecked = false
        binding.chipTvl.isChecked = false
        binding.chipHumss.isChecked = false
        binding.chipAbm.isChecked = false
        binding.chipShs.isChecked =false
    }


    private fun addCourseToSharedPreferences(course: String) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val coursesSet = sharedPreferences.getStringSet("courses", HashSet())?.toMutableSet() ?: HashSet()
        coursesSet.add(course)
        editor.putStringSet("courses", coursesSet)
        editor.apply()
    }

    // Function to remove a course directly from SharedPreferences
    private fun removeCourseFromSharedPreferences(course: String) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val coursesSet = sharedPreferences.getStringSet("courses", HashSet())?.toMutableSet() ?: HashSet()
        coursesSet.remove(course)
        editor.putStringSet("courses", coursesSet)
        editor.apply()
    }

    private fun updateDepartmentInSharedPreferences(department: String) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("department", department)
        editor.apply()
    }

    private fun removeDepartmentFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("department")
        editor.apply()
    }
    private fun hideBottomNav() {
        val translationY = bottomNavigationView.height.toFloat()
        ObjectAnimator.ofFloat(bottomNavigationView, "translationY", 0f, translationY).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        isBottomNavHidden = true
    }

    private fun showBottomNav() {
        ObjectAnimator.ofFloat(bottomNavigationView, "translationY", bottomNavigationView.translationY, 0f).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        isBottomNavHidden = false
    }

    private fun removeDepartment(department: String) {
        val sharedPreferences = requireContext().getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("department")
        editor.apply()
        Log.d("removeDepartment", "Removed department: $department from filter preferences")
    }
}
