package com.mitocode.mitocine.fragments.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mitocode.mitocine.R
import com.mitocode.mitocine.adapters.MovieAdapter
import com.mitocode.mitocine.databinding.FragmentMovieListBinding
import com.mitocode.mitocine.models.Movie

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //declaro e instancio el adapter
        val adapter = MovieAdapter()
        binding.movies.layoutManager = GridLayoutManager(context,2)  //recycler tipo grilla
        //binding.movies.layoutManager = LinearLayoutManager(this) ////recycler tipo lista
        binding.movies.adapter = adapter

        adapter.addItem(Movie("pelicula 1","https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen.jpg"))
        adapter.addItem(Movie("pelicula 2","https://i.blogs.es/d93d8d/marvel/1366_2000.jpeg"))
        adapter.addItem(Movie("pelicula 3","https://hipertextual.com/files/2019/05/hipertextual-avengers-endgame-futuro-capitan-america-2019781893-scaled.jpg"))
        adapter.addItem(Movie("pelicula 4","https://frasesdelavida.com/wp-content/uploads/2018/04/Hulk.jpg"))
        adapter.addItem(Movie("pelicula 5","https://i.blogs.es/7ccbec/iron-man/840_560.jpg"))
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MovieListFragment()
                }
    }