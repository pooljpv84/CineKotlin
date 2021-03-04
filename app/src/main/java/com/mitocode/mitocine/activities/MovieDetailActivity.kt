package com.mitocode.mitocine.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.mitocode.mitocine.R
import com.mitocode.mitocine.databinding.ActivityMovieDetailBinding
import com.mitocode.mitocine.models.Movie

class MovieDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        //titulo en base ak elemento que viene del recyclerview
        //val title = intent.getStringExtra("title")
        //this.title = title

        //RECIBIR STRINGS TIPO OBJETO

        val movie = intent.getParcelableExtra<Movie>("movie")

        if(movie!=null){
            binding.title.text = movie.title
            binding.description.text = movie.description
            Glide.with(this)
                    .load(movie.image)
                    .placeholder(R.mipmap.naruto)
                    .centerCrop()
                    .fitCenter()
                    .into(binding.image)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}