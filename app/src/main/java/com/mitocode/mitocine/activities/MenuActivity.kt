package com.mitocode.mitocine.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mitocode.mitocine.R

import com.mitocode.mitocine.databinding.ActivityMenuBinding

import com.mitocode.mitocine.fragments.MoviesFragment
import com.mitocode.mitocine.fragments.SettingsFragment
import com.mitocode.mitocine.fragments.movies.MovieListFragment

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title=getString(R.string.str_movies)


        //cargar fragmento de peliculas desde el inicio
        changeFraments(MoviesFragment.newInstance())

        //llamar a los fragmentos desde el Menu de opciones
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId)
            {
                R.id.nav_movies -> {
                    title=getString(R.string.str_movies)
                    changeFraments(MoviesFragment.newInstance())
                    true
                }
                R.id.nav_favourite -> {
                    title=getString(R.string.str_favourite)
                    changeFraments(MovieListFragment.newInstance(MovieListFragment.FAVOURITE))
                    true
                }
                R.id.nav_settings -> {

                    title=getString(R.string.str_settings)
                    changeFraments(SettingsFragment.newInstance())
                    true
                }
                else -> {

                    false
                }
            }
        }
    }
    private fun changeFraments(Fragmento: Fragment)
    {
        supportFragmentManager
            .beginTransaction().replace(R.id.container, Fragmento)
            .commit()
    }
}