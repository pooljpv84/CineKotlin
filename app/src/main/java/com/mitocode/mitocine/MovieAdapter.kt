package com.mitocode.mitocine

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.mitocine.databinding.ItemMovieBinding
import java.util.ArrayList

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var binding : ItemMovieBinding
    private lateinit var context : Context
    private val movies = ArrayList<Movie>() //POJO movies

    fun addItem(movie:Movie){
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
            binding.movieImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,R.mipmap.example))
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

}