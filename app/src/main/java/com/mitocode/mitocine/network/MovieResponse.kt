package com.mitocode.mitocine.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieResponse(
    var data: ArrayList<MovieDataResponse>,
    var status: Boolean,
    var message: String
)

data class MovieDataResponse(
    val id: Int,
    val title: String,
    @SerializedName("description")
    val des: String,
    val urlImage: String,
    val releaseDate: String
)
