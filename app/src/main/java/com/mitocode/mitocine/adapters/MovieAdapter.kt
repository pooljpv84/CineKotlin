package com.mitocode.mitocine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mitocode.mitocine.R
import com.mitocode.mitocine.activities.MovieDetailActivity
import com.mitocode.mitocine.databinding.ItemMovieBinding
import com.mitocode.mitocine.models.Movie
import kotlin.collections.ArrayList

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var binding : ItemMovieBinding
    private lateinit var context : Context
    private val movies = ArrayList<Movie>() //POJO movies
    private val moviess = ArrayList<com.mitocode.mitocine.database.Movie>() //POJO movies

    fun addItem(movie: Movie){
       movies.add(movie)
       notifyDataSetChanged()
    }
    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) //clase oara retornar en Oncreate
    {
        fun bind(position: Int)
        {
            val title = movies[position].title
            val image = movies[position].image

            binding.movieTitle.text = title
            //COLOCAR IMAGEN EN UN IMAGEVIEW
            /*binding.movieImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,R.mipmap.example))*/
            //COLOCAR IMAGEN EN UN IMAGEVIEW PERO CON LIBRERIA GLIDE
            //val url : String = ""
            Glide.with(context)
                .load(image)
                .placeholder(R.mipmap.naruto)
                .centerCrop()
                .fitCenter()
                .into(binding.movieImage)
            //imagen desde archivo
            //Glide.with(context).load(R.mipmap.example).centerCrop().fitCenter().into(binding.movieImage)
            //al dar click en un elemento del recycler
            binding.root.setOnClickListener{
                val intent = Intent(context,MovieDetailActivity::class.java)
                intent.putExtra("movie",movies[position]) //ENVIAR OBJETO PARCEABLE A LA OTRA ACTIVIDAD
                context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Este método sirve para inicializar el diseño
                //inflar en base al contexto del diseño individual
                binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
                context = parent.context
                return MovieViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) 
    {
        //Vincular diseño con la información (crear cada vista)
        if (holder is MovieViewHolder)
        {
            holder.bind(position)
        }
    }


    override fun getItemCount(): Int {
        //cantidad de elementos que va a tener el RecyclerView
        return movies.size
    }

    fun addItems(movies: ArrayList<Movie>)
    {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }


}