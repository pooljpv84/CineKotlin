package com.mitocode.mitocine.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import com.mitocode.mitocine.R
import com.mitocode.mitocine.databinding.ActivitySplashBinding
import com.mitocode.mitocine.preferences.SharedPreferencesHelper

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hilos
        val handler = Handler()
        handler.postDelayed({
            val intent : Intent
            var xx : String = SharedPreferencesHelper.getUserName(this).toString()
            Log.i("sms",xx)
            if (SharedPreferencesHelper.getUserName(this).isNullOrBlank())
            {

                intent = Intent(this,MainActivity::class.java)
            }else {
                intent = Intent(this,MenuActivity::class.java)
            }
            startActivity(intent)
            finish()
        },2000)
    }


}