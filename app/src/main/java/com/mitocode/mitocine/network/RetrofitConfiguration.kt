package com.mitocode.mitocine.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfiguration
{
    companion object{
        fun getConfiguration():Retrofit{
            return Retrofit.Builder()
                .baseUrl("http://jcodee.com:8080/MovieWS/")  //apuntar al WS
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}