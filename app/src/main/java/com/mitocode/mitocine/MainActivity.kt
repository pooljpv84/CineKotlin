package com.mitocode.mitocine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitocode.mitocine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //CLIK EN EL BOTON
        binding.btnlogin.setOnClickListener {
            val username = binding.username.text
            val password = binding.password.text

            //validar el material design del texto
            if (username.isNullOrBlank())
                binding.usernameTil.error = "El campo usuario es requerido"
            else
                binding.usernameTil.error = null
            if (password.isNullOrBlank())
                binding.passwordTil.error = "El campo contraseña es requerido"
            else
                binding.passwordTil.error = null
        }
    }
}