    package com.mitocode.mitocine.preferences

import android.content.Context

class SharedPreferencesHelper
{

    companion object
    {
        const val PREFERENCES_NAME = "mitocine-preferences"
        const val KEY_USERNAME = "username"

    fun addUsername(context: Context, username :String)
    {
        val edit=context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE).edit()
        edit.putString(KEY_USERNAME,username)
        edit.apply()
    }
    fun getUserName(context: Context):String?
    {
        val preferences=context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return preferences.getString(KEY_USERNAME,"")
    }
    fun deleteAll(context: Context)
    {
        val edit = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE).edit()
        edit.clear()
        edit.apply()

    }
    }
}