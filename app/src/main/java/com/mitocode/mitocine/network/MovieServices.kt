package com.mitocode.mitocine.network

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieServices
{
    //MAPEO DE SERVICIOS
    @GET("movie/premieres")
    fun getMoviePremieres():Call<MovieResponse>

    //uso parametros porque se ingresan en el body
    @POST("user")
    @FormUrlEncoded
    fun saveUser(name:String,
                 lastName:String,
                 username:String,
                 password:String,
                 email:String,
                 address:String,
                 phone:String):Call<UserResponse>
}