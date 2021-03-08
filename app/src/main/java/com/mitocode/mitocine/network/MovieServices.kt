package com.mitocode.mitocine.network

import retrofit2.Call
import retrofit2.http.Field
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
    fun saveUser(
        @Field("name")
        name:String,
        @Field("last_name")
        lastName:String,
        @Field("username")
        username:String,
        @Field("password")
        password:String,
        @Field("email")
        email:String,
        @Field("address")
        address:String,
        @Field("phone")
        phone:String
    ):Call<UserResponse>

    @POST("user/validate")
    @FormUrlEncoded
    fun validateUser(
        @Field("username")
        username:String,
        @Field("password")
        password:String
    ): Call<UserResponse>

    //respuesta a Peliculas proximamente
    @GET("movie/other")
    fun getMovieOther():Call<MovieResponse>

}