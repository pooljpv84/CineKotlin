package com.mitocode.mitocine.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Person
{
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name= "name")
    var name: String =""

    @ColumnInfo(name= "last_name")
    var lastName: String=""

    @ColumnInfo(name= "address")
    var address: String=""

    @ColumnInfo(name= "password")
    var password: String=""

    @ColumnInfo(name= "email")
    var email: String=""

    @ColumnInfo(name= "phone")
    var phone: String=""

    @ColumnInfo(name= "username")
    var username: String=""


}