package com.mitocode.mitocine.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    @Insert
    fun insert(person: Person)

    @Query("select * from person where username=:username and password=:password")
    fun validateUser(username: String, password: String):Person

    @Query("select * from person where username=:username")
    fun existsUser(username: String):Person

    @Query("select * from person")
    fun getAll():List<Person>


}