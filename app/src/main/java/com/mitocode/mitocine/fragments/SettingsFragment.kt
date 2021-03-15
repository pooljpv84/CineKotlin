package com.mitocode.mitocine.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.mitocode.mitocine.R
import com.mitocode.mitocine.activities.MainActivity
import com.mitocode.mitocine.activities.MapsActivity
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.FragmentSettingsBinding
import com.mitocode.mitocine.preferences.SharedPreferencesHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Mostrar los datos de la BDD en las cajas de texto
        //Consumir en otro hilo
            doAsync {
                val username = SharedPreferencesHelper.getUserName(requireContext())

                    val person =AppDatabase.getInstance(requireContext()).personDao().existsUser(username.toString())
                    if (person !=null)
                    {
                        //volver al hilo principal e ir al diseño principal del Activity
                        uiThread {
                            binding.username.setText(person.username)
                            binding.name.setText(person.name)
                            binding.lastname.setText(person.lastName)
                            binding.email.setText(person.email)
                            binding.phone.setText(person.phone)

                        }
                    }
            }
        //

        Glide.with(requireContext())
                .load("https://www.nicepng.com/png/detail/209-2099655_anime-avatar-png-avatar-gamer.png")
                .placeholder(R.mipmap.profile)
                .centerCrop()
                .fitCenter()
                .circleCrop()
                .into(binding.profile)
        binding.logout.setOnClickListener {
            //ELIMINAR LAS PREFERENCIAS DEL USUSARIO
            SharedPreferencesHelper.deleteAll(requireContext())
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.requireActivity().finish()
        }

        binding.changeAddress.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
        binding.changeAddressGoogle.setOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            //cargar el buscador
            val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN,
                    fields
            ).setCountry("ec")
                .build(requireContext())
            startActivityForResult(intent, 123)
        }

        binding.selectImage.setOnClickListener {

            showOptionAlert()
        }
    }

    private fun showOptionAlert()
    {
        //alerta de camara o galeria
        AlertDialog.Builder(requireContext())
                .setItems(
                        arrayOf(
                                "Camara",
                                "Galeria"
                        )
                ) {
                    dialogInterface: DialogInterface, position: Int ->
                    when (position)
                    {
                        0 -> {
                            checkCameraPermissions()
                        }
                        1 -> {
                            checkGaleryPermissions()
                        }
                }
        }.setTitle("Seleccione una opción").show()
    }

    @AfterPermissionGranted(RC_GALLERY)
    private fun checkGaleryPermissions()
    {
        val perms = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            showGallery()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_rationale),
                    RC_GALLERY, *perms)
        }
    }


    private fun showGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        intent.resolveActivity(requireActivity().packageManager)
        startActivityForResult(intent,REQUEST_CODE_GALLERY)
    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun checkCameraPermissions()
    {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode)
        {
            //COLOCAR LA IMAGEN SELECCIONADA EN EL IMAGEVIEW
            REQUEST_CODE_GALLERY->{
                if (resultCode == Activity.RESULT_OK)
                {
                    data?.data?.let { uri: Uri ->
                        binding.profile.setImageURI(uri)
                    }
                }
            }
            123 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val placeResponse = Autocomplete.getPlaceFromIntent(data!!)
                    //obtener el nombre
                    val name = placeResponse.name
                    val latLng = placeResponse.latLng
                    val address = placeResponse.address

                    Toast.makeText(requireContext(), "Name: $name\n" +
                            "Latitude: ${latLng?.latitude}\n" +
                            "Longitude: ${latLng?.longitude}\n" +
                            "Address: $address", Toast.LENGTH_LONG).show()

                    goToMap(name, latLng, address)
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    val error = Autocomplete.getStatusFromIntent(data!!)
                    Log.d("sms", "Error: ${error.statusMessage}")
                }
            }
        }
    }

    private fun goToMap(name: String?, latLng: LatLng?, address: String?)
    {
       val intent = Intent(requireContext(), MapsActivity::class.java)

        intent.putExtra("name", name)
        intent.putExtra("latitude", latLng?.latitude)
        intent.putExtra("longitude", latLng?.longitude)
        intent.putExtra("address", address)

        startActivity(intent)
    }

    companion object {
        const val RC_GALLERY=122
        const val RC_CAMERA=124
        const val REQUEST_CODE_GALLERY=100

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}