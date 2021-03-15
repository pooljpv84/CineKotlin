package com.mitocode.mitocine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao
{
    @Insert
    fun insert(movie: Movie)

    @Query("select * from movie")
    fun getAll():List<Movie>

    @Delete
    fun deleteMovie(movie: Movie)
}