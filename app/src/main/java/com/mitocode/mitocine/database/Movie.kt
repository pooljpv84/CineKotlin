package com.mitocode.mitocine.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Movie
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name= "title")
    var title: String =""

    @ColumnInfo(name= "description")
    var description: String=""

    @ColumnInfo(name= "urlImage")
    var urlImage: String=""

}