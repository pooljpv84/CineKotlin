package com.mitocode.mitocine.fragments.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mitocode.mitocine.R
import com.mitocode.mitocine.adapters.MovieAdapter
import com.mitocode.mitocine.databinding.FragmentMovieListBinding
import com.mitocode.mitocine.databinding.LoadingBinding
import com.mitocode.mitocine.models.Movie
import com.mitocode.mitocine.network.MovieDataResponse
import com.mitocode.mitocine.network.MovieResponse
import com.mitocode.mitocine.network.MovieServices
import com.mitocode.mitocine.network.RetrofitConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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
    lateinit var adapter:MovieAdapter

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
        adapter = MovieAdapter()
        binding.movies.layoutManager = GridLayoutManager(context,2)  //recycler tipo grilla
        //binding.movies.layoutManager = LinearLayoutManager(this) ////recycler tipo lista
        binding.movies.adapter = adapter

        //validar las constantes en el cambio de Favoritos--Proximamamente y Cartelera

        when(param1)
        {
            MOVIE ->{loadMovie()}
            SOON ->{loadSoon()}
            FAVOURITE ->{loadFavourite()}
        }

    }

    private fun loadFavourite()
    {
        validateEmpty()
    }

    private fun loadSoon()
    {
        validateEmpty()
    }

    private fun loadMovie()
    {
        val bindingLoading = binding.loadingLayout
        bindingLoading.loading.visibility = View.VISIBLE

        //IMAGENES QUEMADAS
        /*adapter.addItem(Movie("Pelicula 1","https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen.jpg","aaaaa"))
        adapter.addItem(Movie("Pelicula 2","https://i.blogs.es/d93d8d/marvel/1366_2000.jpeg","bbbb"))
        adapter.addItem(Movie("Pelicula 3","https://hipertextual.com/files/2019/05/hipertextual-avengers-endgame-futuro-capitan-america-2019781893-scaled.jpg","cccc"))
        adapter.addItem(Movie("Pelicula 4","https://frasesdelavida.com/wp-content/uploads/2018/04/Hulk.jpg","dddd"))
        adapter.addItem(Movie("Pelicula 5","https://i.blogs.es/7ccbec/iron-man/840_5ggg60.jpg","eeeee"))
        */
        //base url y luego la interfaz con el metodo get
        val retrofit = RetrofitConfiguration.getConfiguration().create(MovieServices::class.java)
        //decalar a:(movie/premieres)
        val call = retrofit.getMoviePremieres()
        //instanciar
        call.enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>)
            {
                //200
                Log.i("sms","Onresponse")
                //decodificar informacion
                val body = response.body()
                if (body!=null){
                    bindingLoading.loading.visibility = View.GONE
                    if (body.status){
                        val data: ArrayList<MovieDataResponse> = body.data
                        //convertir clase MovieDataRepsonse a clase Movie para poder agregar
                        val movies = convertResponseMovie(data)
                        //llamar al adaptador
                        adapter.addItems(movies)
                        validateEmpty()

                    }else{

                        validateEmpty()
                    }

                }

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable)
            {
                //!=200
                Log.i("sms","OnFailure")
            }
        })


    }

    private fun convertResponseMovie(data: ArrayList<MovieDataResponse>): ArrayList<Movie>
    {
        val response = ArrayList<Movie>()
        if (data!=null)
        {
            for (item in data){
                response.add(Movie(item.title, item.urlImage, item.des))
            }
        }
    return response
    }


    private fun validateEmpty() {
        if (adapter.itemCount == 0){binding.linearEmpty.visibility = View.VISIBLE}
        else{binding.linearEmpty.visibility = View.GONE}
    }

    companion object {
        const val MOVIE = "MOVIE"
        const val SOON = "SOON"
        const val FAVOURITE = "FAVOURITE"

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
        fun newInstance(param1: String) =
                MovieListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1,param1)

                    }
                }
    }
}