package com.mitocode.mitocine.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.ActivityMainBinding
import com.mitocode.mitocine.preferences.SharedPreferencesHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

            //validar la existncia del ususario y la password en la BDD
            doAsync {
                val database = AppDatabase.getInstance(this@MainActivity)
                val personResponse = database.personDao().validateUser(username,password)
                uiThread {
                   if (personResponse?.username !=null)
                   {
                       //almacenar user en  preferencias
                       SharedPreferencesHelper.addUsername(this@MainActivity,username)

                       Log.d("sms", personResponse.username + "  " +personResponse.name)
                       //ir a la cartelera
                       val intent = Intent(applicationContext, MenuActivity::class.java)
                       startActivity(intent)
                   }
                    else{
                        Toast.makeText(this@MainActivity,"Usuario yo clave incorrecto",Toast.LENGTH_SHORT).show()
                    }
                }
            }




        }
        //click en registrar nuevo usuario
        binding.textViewRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}