package com.example.dietapp.objects

import android.content.Context
import android.text.format.DateFormat
import com.example.dietapp.R
import java.util.*

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

    fun getTextViewDate(date: Date): String{

        val dayOfTheWeek = DateFormat.format("EEEE", date) as String
        val day = DateFormat.format("dd", date) as String
        val month = DateFormat.format("MM", date) as String
        val year = DateFormat.format("yyyy", date) as String

        val properDayOfTheWeek = when (dayOfTheWeek) {
            "poniedziałek" -> "Pon"
            "wtorek" -> "Wt"
            "środa" -> "Śr"
            "czwartek" -> "Czw"
            "piątek" -> "Pt"
            "sobota" -> "Sb"
            "niedziela" -> "Nd"
            "Monday" -> "Pon"
            "Tuesday" -> "Wt"
            "Wednesday" -> "Śr"
            "Thursday" -> "Czw"
            "Friday" -> "Pt"
            "Saturday" -> "Sb"
            "Sunday" -> "Nd"
            else -> ""
        }
        val properMonth = when (month) {
            "01" -> "sty"
            "02" -> "lut"
            "03" -> "mar"
            "04" -> "kwi"
            "05" -> "maj"
            "06" -> "cze"
            "07" -> "lip"
            "08" -> "sie"
            "09" -> "wrz"
            "10" -> "paź"
            "11" -> "lis"
            "12" -> "gru"
            else -> {
                print("ERROR - Zły miesiąc")
            }
        }

        if(properDayOfTheWeek == ""){
            return "$day $properMonth"
        }

        return "$properDayOfTheWeek $day $properMonth"

    }

    fun databaseDate(date: Date): String{

        val day = DateFormat.format("dd", date) as String
        val month = DateFormat.format("MM", date) as String
        val year = DateFormat.format("yyyy", date) as String

        return "$day-$month-$year"

    }

    fun getMealName(meal: String): String {
        return when (meal) {
            "breakfast" -> "Śniadanie"
            "secondbreakfast" -> "Drugie śniadanie"
            "lunch" -> "Obiad"
            "snack" -> "Przekąska"
            "dinner" -> "Kolacja"
            else -> ""
        }


    }





}