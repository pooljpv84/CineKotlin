package com.mitocode.mitocine.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mitocode.mitocine.R
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.ActivityRegisterBinding
import com.mitocode.mitocine.network.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


        val person = com.mitocode.mitocine.database.Person()
        //carga de los textos al OBJ
        person.username = username
        person.name = name
        person.lastName = lastName
        person.email = email
        person.address = address
        person.phone = phone
        person.password = password
        /*
        //------------------------INSERTAR EN LA BDD INTERNA-------------------------------
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
        //------------------------FIN DE INSERTAR EN LA BDD INTERNA-------------------------------//

        //----------------INSERTAR DATA EN LA BDD EXTERNA---------------//

        //base url y luego la interfaz con el metodo get
        val retrofit = RetrofitConfiguration.getConfiguration().create(MovieServices::class.java)
        //decalar a:(movie/premieres)
        val call = retrofit.saveUser(person.name, person.lastName,
            person.username, person.password, person.email,
            person.address, person.phone)
        //instanciar
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                //200
                //200
                Log.i("sms","Onresponse")
                //decodificar informacion
                val body = response.body()
                if (body!=null){
                    if (body.status)
                    {
                        val data: UserDataResponse = body.data
                        //validar el ID
                        if (data.id>0)
                        {
                            Toast.makeText(this@RegisterActivity, "Se registro la persona", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@RegisterActivity, "Ocurrió un error", Toast.LENGTH_SHORT).show()
                    }


                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                //!=200
            }


        })


        //----------------FIN INSERTAR DATA EN LA BDD EXTERNA---------------//






    }
}