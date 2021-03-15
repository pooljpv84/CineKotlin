 package com.mitocode.mitocine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

 @Database (version = 1,entities = [Person::class,Movie::class],exportSchema = false)

public abstract class AppDatabase: RoomDatabase() {
    public abstract fun personDao(): PersonDao
     public abstract fun movieDao(): MovieDao

    //INSTANCIAS
    companion object {
        private var instance: AppDatabase? = null

         @Synchronized
        fun getInstance(context: Context):AppDatabase{
            if (instance == null)
            {
                instance = Room.databaseBuilder(context,AppDatabase::class.java,"mitocine")
                    .build()
            }
            return instance!!
        }
    }
}