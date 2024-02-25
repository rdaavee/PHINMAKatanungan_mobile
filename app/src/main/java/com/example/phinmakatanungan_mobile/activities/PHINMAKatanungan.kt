package com.example.phinmakatanungan_mobile

import android.app.Application
import com.example.phinmakatanungan_mobile.api.PHINMAClient.setSharedPreferences

class PHINMAKatanungan : Application() {
    override fun onCreate() {
        super.onCreate()

        // THIS CLASS IS RESPONSIBLE PARA SA PAG INITIALIZE NG SHAREDPREFERENCE CONGIFS. PLEASE DONT TOUCH IT TY


        // initialize SharedPreferences
        val sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE)

        // set SharedPreferences for PHINMAClient
        setSharedPreferences(sharedPreferences)
    }
}
