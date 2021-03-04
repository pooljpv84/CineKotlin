package com.mitocode.mitocine.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import com.mitocode.mitocine.R
import com.mitocode.mitocine.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hilos
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}