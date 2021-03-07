package com.mitocode.mitocine.network

import retrofit2.Call
import retrofit2.http.GET

interface MovieServices
{
    @GET("movie/premieres")
    fun getMoviePremieres():Call<MovieResponse>
}