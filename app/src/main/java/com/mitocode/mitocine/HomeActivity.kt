package com.mitocode.mitocine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.mitocode.mitocine.adapters.MovieAdapter
import com.mitocode.mitocine.databinding.ActivityHomeBinding
import com.mitocode.mitocine.models.Movie

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //declaro e instancio el adapter
        val adapter = MovieAdapter()
          binding.movies.layoutManager = GridLayoutManager(this,2)  //recycler tipo grilla
        //binding.movies.layoutManager = LinearLayoutManager(this) ////recycler tipo lista
        binding.movies.adapter = adapter

        adapter.addItem(Movie("pelicula 1","https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen.jpg"))
        adapter.addItem(Movie("pelicula 2","https://i.blogs.es/d93d8d/marvel/1366_2000.jpeg"))
        adapter.addItem(Movie("pelicula 3","https://hipertextual.com/files/2019/05/hipertextual-avengers-endgame-futuro-capitan-america-2019781893-scaled.jpg"))
        adapter.addItem(Movie("pelicula 4","https://frasesdelavida.com/wp-content/uploads/2018/04/Hulk.jpg"))
        adapter.addItem(Movie("pelicula 5","https://i.blogs.es/7ccbec/iron-man/840_560.jpg"))
    }
}