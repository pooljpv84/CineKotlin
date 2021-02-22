package com.mitocode.mitocine.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mitocode.mitocine.HomeActivity
import com.mitocode.mitocine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //CLIK EN EL BOTON
        binding.btnlogin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            //validar el material design del texto
            if (username.isBlank())
                binding.usernameTil.error = "El campo usuario es requerido"
            else
                binding.usernameTil.error = null
            if (password.isBlank())
                binding.passwordTil.error = "El campo contraseña es requerido"
            else
                binding.passwordTil.error = null

            //ir a la cartelera
            val intent = Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent)
        }
        //click en registrar nuevo usuario
        binding.textViewRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}