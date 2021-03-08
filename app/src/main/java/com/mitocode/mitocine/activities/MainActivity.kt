package com.mitocode.mitocine.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.mitocode.mitocine.R
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.ActivityMainBinding
import com.mitocode.mitocine.network.MovieServices
import com.mitocode.mitocine.network.RetrofitConfiguration
import com.mitocode.mitocine.network.UserResponse
import com.mitocode.mitocine.preferences.SharedPreferencesHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            ////BDD EXTERNA
            val retrofit = RetrofitConfiguration.getConfiguration().create(MovieServices::class.java)
            val call = retrofit.validateUser(username,password)
            call.enqueue(object : Callback<UserResponse>{
                override fun onResponse(call: Call<UserResponse>,response: Response<UserResponse>) {
                    val body = response.body()
                    if (body!=null){
                        if (body.status)
                        {
                            val data = body.data
                            if (data != null && data.id>0)
                            {
                                SharedPreferencesHelper.addUsername(this@MainActivity,username)
                                //ir a la cartelera
                                val intent = Intent(applicationContext, MenuActivity::class.java)
                                startActivity(intent)
                            }
                            else
                            {
                                Toast.makeText(this@MainActivity,"Usuario y/o clave incorrecto",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            val dialog = AlertDialog.Builder(this@MainActivity)
                            dialog.setTitle("Alerta")
                            dialog.setMessage("Usuario yo clave incorrecto")
                            dialog.setCancelable(false)
                            dialog.setPositiveButton("Si"){ dialog, i ->
                                val dialogCustom = Dialog(this@MainActivity)
                                dialogCustom.setContentView(R.layout.dialog)
                                dialogCustom.window!!.setBackgroundDrawable(ColorDrawable(0))
                                dialogCustom.setCancelable(false)

                                val title = dialogCustom.findViewById<TextView>(R.id.title)
                                title.text="Nueva Alerta"

                                val leftButton = dialogCustom.findViewById<TextView>(R.id.botonLeft)
                                leftButton.setOnClickListener {
                                    dialogCustom.dismiss()
                                }
                                dialogCustom.show()

                            }
                            dialog.setNegativeButton("No"){ dialog, i ->
                                val intent = Intent(this@MainActivity, MenuActivity::class.java)
                                startActivity(intent)

                            }
                            dialog.setNeutralButton("Neutral"){ dialog, i ->
                            }
                            dialog.show()



                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                }

            })
            //CIERRO BDD EXTERNA
            /* ----------- BDD LOCAL---------------------
            //validar la existncia del ususario y la password en la BDD
            doAsync {
                val database = AppDatabase.getInstance(this@MainActivity)
                val personResponse = database.personDao().validateUser(username,password)
                uiThread {
                    if (personResponse?.username !=null)
                    {
                        //almacenar user en  preferencias
                        SharedPreferencesHelper.addUsername(this@MainActivity,username)

                        Log.i("sms", personResponse.username + "  " +personResponse.name)
                        //ir a la cartelera
                        val intent = Intent(applicationContext, MenuActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@MainActivity,"Usuario yo clave incorrecto",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            ////////////////////////////////////FIN BDD LOCAL
            */


        }
        //click en registrar nuevo usuario
        binding.textViewRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}