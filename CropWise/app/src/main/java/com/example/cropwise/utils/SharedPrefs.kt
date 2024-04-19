package com.example.cropwise.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


object SharedPrefs {

    fun storeData(firstName: String,lastName: String,email: String,context : Context) {
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("email", email)
        editor.apply()
    }

    fun getFirstName(context: Context) :String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("firstName", "")!!
    }

    fun getLastName(context: Context) :String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("lastName", "")!!
    }

    fun getEmail(context: Context) :String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email", "")!!
    }
}