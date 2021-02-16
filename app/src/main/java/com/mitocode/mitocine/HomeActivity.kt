package com.mitocode.mitocine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.mitocine.databinding.ActivityHomeBinding
import com.mitocode.mitocine.databinding.ActivityRegisterBinding

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

        adapter.addItem(Movie("pelicula 1",""))
        adapter.addItem(Movie("pelicula 2",""))
        adapter.addItem(Movie("pelicula 3",""))
        adapter.addItem(Movie("pelicula 4",""))
        adapter.addItem(Movie("pelicula 5",""))
    }
}