package com.example.dietapp

import CurrentDayFragment
import FavouriteProductsFragment
import MyDietsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.cesarferreira.tempo.Tempo
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false);

        //supportActionBar?.hide()

        //val toolbar:Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        val myDiets = MyDietsFragment()
        val currentDay = CurrentDayFragment()
        val favourites = FavouriteProductsFragment()
        val dateView: CardView = findViewById<View>(R.id.dateToolbar) as CardView

        val currentDate: Date = Tempo.now
        Log.e("dzisiaj", currentDate.toString())




        val curDay : View = bottomNav.findViewById(R.id.bottom_nav_current_day)
        curDay.performClick()

        selectFragment(currentDay)

        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_nav_current_day->{selectFragment(currentDay)
                    dateView.visibility = View.VISIBLE}
                R.id.bottom_nav_my_diets->{selectFragment(myDiets)
                    dateView.visibility = View.GONE}
                R.id.bottom_nav_favourite->{selectFragment(favourites)
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
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            Toast.makeText(applicationContext,"ustawienia",Toast.LENGTH_SHORT).show()
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
            commit()
        }


}