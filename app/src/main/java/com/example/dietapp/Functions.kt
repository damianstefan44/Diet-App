package com.example.dietapp

import android.content.Context

object Functions {


    fun saveDate(context: Context, date: String) {
        val sharedPrefsKey = context.getString(R.string.shared_preferences)
        val dateKey: String = context.getString(R.string.saved_date)

        val sharedPreferences = context.getSharedPreferences(sharedPrefsKey, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(dateKey, date)
            apply()
        }
    }

    fun getDate(context: Context): String? {
        val sharedPrefsKey = context.getString(R.string.shared_preferences)
        val dateKey: String = context.getString(R.string.saved_date)
        val sharedPreferences = context.getSharedPreferences(sharedPrefsKey, Context.MODE_PRIVATE)
        val date = sharedPreferences.getString(dateKey, "error")
        if (date.equals("error")) {
            return null
        }
        return date
    }


    fun deleteDate(context: Context) {
        val sharedPrefsKey = context.getString(R.string.shared_preferences)
        val dateKey: String = context.getString(R.string.saved_date)
        val sharedPreferences = context.getSharedPreferences(sharedPrefsKey, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(dateKey)
            apply()
        }
    }







}