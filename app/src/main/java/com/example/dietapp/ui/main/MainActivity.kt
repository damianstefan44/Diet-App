package com.example.dietapp.ui.main

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.days
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.plus
import com.example.dietapp.R
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

private val uid = FirebaseAuth.getInstance().uid ?: ""
var fragment: String = ""

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //supportActionBar?.hide()

        //val toolbar:Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        val myDiets = MyDietsFragment()
        val currentDay = CurrentDayFragment()
        val favourites = FavouriteProductsFragment()
        val dateView: CardView = findViewById<View>(R.id.dateToolbar) as CardView
        var currDate: TextView = findViewById(R.id.dateCurrentDay)
        val prevDate: ImageView = findViewById(R.id.datePreviousDay)
        val nextDate: ImageView = findViewById(R.id.dateNextDay)

        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var currentDate: Date = Tempo.now
        currDate.text = getTextViewDate(currentDate)
        Log.e("dzisiaj", currentDate.toString())
        Log.e("dzisiaj", databaseDate(currentDate))
        Functions.saveDate(this, Functions.databaseDate(currentDate))

        val curDay : View = bottomNav.findViewById(R.id.bottom_nav_current_day)
        val favProds : View = bottomNav.findViewById(R.id.bottom_nav_favourite)
        val diets : View = bottomNav.findViewById(R.id.bottom_nav_my_diets)

        when(Functions.getFragment(applicationContext)){
            "fav" ->{favProds.performClick()
                selectFragment(favourites)}
            "diets" ->{diets.performClick()
                selectFragment(myDiets)}
            "cur" ->{curDay.performClick()
                selectFragment(currentDay)}
        }

        prevDate.setOnClickListener {
            currentDate -= 1.days
            Functions.saveDate(this, Functions.databaseDate(currentDate))
            currDate.text = getTextViewDate(currentDate)
            currentDay.refreshMeals(applicationContext)
        }

        nextDate.setOnClickListener {
            currentDate += 1.days
            Functions.saveDate(this, Functions.databaseDate(currentDate))
            currDate.text = getTextViewDate(currentDate)
            currentDay.refreshMeals(applicationContext)


        }
        currDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->
                currentDate = Tempo.with(year,monthOfYear+1,dayOfMonth)
                currDate.text = getTextViewDate(currentDate)
            }, year, month, day)
            Functions.saveDate(this, Functions.databaseDate(currentDate))
            datePickerDialog.show()
            currentDay.refreshMeals(applicationContext)

        }

        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_nav_current_day ->{selectFragment(currentDay)
                    dateView.visibility = View.VISIBLE}
                R.id.bottom_nav_my_diets ->{selectFragment(myDiets)
                    dateView.visibility = View.GONE}
                R.id.bottom_nav_favourite ->{selectFragment(favourites)
                    dateView.visibility = View.GONE}

            }
            true
        }
    }
    


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)


            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun selectFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentSelector,fragment)
            commitNow()
        }

    private fun getTextViewDate(date: Date): String{

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

    private fun databaseDate(date: Date): String{

        val day = DateFormat.format("dd", date) as String
        val month = DateFormat.format("MM", date) as String
        val year = DateFormat.format("yyyy", date) as String

        return "$day-$month-$year"

    }


}