package com.mitocode.mitocine.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mitocode.mitocine.R
import com.mitocode.mitocine.activities.MainActivity
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.FragmentSettingsBinding
import com.mitocode.mitocine.preferences.SharedPreferencesHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
                if (username !=null){
                    val person =AppDatabase.getInstance(requireContext()).personDao().existsUser(username)
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
            val intent = Intent(requireContext(),MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.requireActivity().finish()
        }
    }

    companion object {
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