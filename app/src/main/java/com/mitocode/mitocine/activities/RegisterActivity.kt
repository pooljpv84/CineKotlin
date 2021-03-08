package com.mitocode.mitocine.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.mitocode.mitocine.R
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.ActivityRegisterBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //regreso en el action var
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title= getString(R.string.str_register_now)
        //boton
        binding.register.setOnClickListener {
            //obtener en variables los componentes
            val username = binding.username.text.toString()
            val name = binding.name.text.toString()
            val lastName = binding.lastname.text.toString()
            val password = binding.password.text.toString()
            val address = binding.address.text.toString()
            val email = binding.email.text.toString()
            val phone = binding.phone.text.toString()
            //validaciones
            validaciones(username,name,lastName,address,password,email,phone)


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->
            {
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun validaciones(username:String, name: String,lastName: String, address: String, password: String, email: String, phone: String){
        if (username.isBlank())
            binding.usernameTil.error = getString(R.string.error_username)
        else
            binding.usernameTil.error = null
        if (name.isBlank())
            binding.nameTil.error = "El campo nombre es requerido"
        else
            binding.nameTil.error = null
        if (address.isBlank())
            binding.addressTil.error = "El campo dirección es requerido"
        else
            binding.addressTil.error = null
        if (password.isBlank())
            binding.passwordTil.error = "El campo contraseña es requerido"
        else
            binding.passwordTil.error = null
        if (lastName.isBlank())
            binding.lastnameTil.error = "El campo apellido es requerido"
        else
            binding.lastnameTil.error = null
        if (email.isBlank())
            binding.emailTil.error = "El campo email  es requerido"
        else
            binding.emailTil.error = null
        if (phone.isBlank())
            binding.phoneTil.error = "El campo teléfono es requerido"
        else
            binding.phoneTil.error = null

        /*
        //------------------------INSERTAR EN LA BDD INTERNA-------------------------------
        val person = com.mitocode.mitocine.database.Person()
        //carga de los textos al OBJ
        person.username = username
        person.name = name
        person.lastName = lastName
        person.email = email
        person.address = address
        person.phone = phone
        person.password = password

        //asincronia
        doAsync {
            val database = AppDatabase.getInstance(this@RegisterActivity)

            //validar la existencia del usuario
            if (database.personDao().existsUser(username) == null) //si es nulo inserta en la bdd
            {
                database.personDao().insert(person)
                uiThread {
                    Toast.makeText(this@RegisterActivity, "Se registro la persona", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                uiThread {
                    Toast.makeText(this@RegisterActivity, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                }
            }

        }*/
        //------------------------FIN DE INSERTAR EN LA BDD INTERNA-------------------------------



    }
}