package com.mitocode.mitocine.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.mitocode.mitocine.R
import com.mitocode.mitocine.database.AppDatabase
import com.mitocode.mitocine.databinding.ActivityMovieDetailBinding
import com.mitocode.mitocine.models.Movie
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.random.Random

class MovieDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    var moviee = com.mitocode.mitocine.database.Movie()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        //constaint
        val clParent = findViewById<ConstraintLayout>(R.id.detailConstraint)
        val mConstraintSet = ConstraintSet()

        //
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
        //setear objeto

        //carga de los textos al OBJ

        if (movie != null) {
            moviee.id = movie.id
            moviee.title = movie?.title.toString()
            moviee.description = movie?.description.toString()
            moviee.urlImage = movie?.image.toString()
        }

        //click en favorito
        binding.heart.setOnClickListener {
            binding.heart2.visibility = View.VISIBLE
            binding.heart.visibility = View.GONE
            Log.i("smns","kk0")
            //modifcar constraint
            mConstraintSet.clone(clParent)
            mConstraintSet.connect(R.id.title, ConstraintSet.END, R.id.heart2, ConstraintSet.END)
            mConstraintSet.applyTo(clParent)
            ////
            //------------------------INSERTAR EN LA BDD INTERNA-------------------------------



            //asincronia
            doAsync {
                val database = AppDatabase.getInstance(this@MovieDetailActivity)
                    database.movieDao().insert(moviee)
                    uiThread {
                        Toast.makeText(this@MovieDetailActivity, "Se registro la pelicula en favoritos", Toast.LENGTH_SHORT).show()
                    }
            }

            //------------------------FIN DE INSERTAR EN LA BDD INTERNA-------------------------------//
        }
        binding.heart2.setOnClickListener {
            binding.heart2.visibility = View.GONE
            binding.heart.visibility = View.VISIBLE


            //asincronia
            doAsync {
                val database = AppDatabase.getInstance(this@MovieDetailActivity)

                database.movieDao().deleteMovie(moviee)
                uiThread {
                    Toast.makeText(this@MovieDetailActivity, "Se elimino la pelicula de favoritos", Toast.LENGTH_SHORT).show()
                }
            }

            //------------------------FIN DE INSERTAR EN LA BDD INTERNA-------------------------------//
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