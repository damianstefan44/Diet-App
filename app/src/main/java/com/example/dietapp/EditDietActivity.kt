package com.example.dietapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditDietActivity : AppCompatActivity() {

    private var breakfastNameList = mutableListOf<String>()
    private var breakfastWeightList = mutableListOf<String>()
    private var breakfastCaloriesList = mutableListOf<Int>()
    private var breakfastEatenList = mutableListOf<Boolean>()

    private var secondBreakfastNameList = mutableListOf<String>()
    private var secondBreakfastWeightList = mutableListOf<String>()
    private var secondBreakfastCaloriesList = mutableListOf<Int>()
    private var secondBreakfastEatenList = mutableListOf<Boolean>()

    private var lunchNameList = mutableListOf<String>()
    private var lunchWeightList = mutableListOf<String>()
    private var lunchCaloriesList = mutableListOf<Int>()
    private var lunchEatenList = mutableListOf<Boolean>()

    private var snackNameList = mutableListOf<String>()
    private var snackWeightList = mutableListOf<String>()
    private var snackCaloriesList = mutableListOf<Int>()
    private var snackEatenList = mutableListOf<Boolean>()

    private var dinnerNameList = mutableListOf<String>()
    private var dinnerWeightList = mutableListOf<String>()
    private var dinnerCaloriesList = mutableListOf<Int>()
    private var dinnerEatenList = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_diet)

        val breakfast: RecyclerView = findViewById<View>(R.id.edit_breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = findViewById<View>(R.id.edit_secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = findViewById<View>(R.id.edit_lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = findViewById<View>(R.id.edit_snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = findViewById<View>(R.id.edit_dinnerRecyclerView) as RecyclerView

        val saveButton: Button = findViewById(R.id.edit_save_button)

        breakfast.layoutManager = LinearLayoutManager(applicationContext)
        breakfast.adapter = ProductAdapter(breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
        //breakfast.isNestedScrollingEnabled = false

        secondBreakfast.layoutManager = LinearLayoutManager(applicationContext)
        secondBreakfast.adapter = ProductAdapter(secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
        //secondBreakfast.isNestedScrollingEnabled = false

        lunch.layoutManager = LinearLayoutManager(applicationContext)
        lunch.adapter = ProductAdapter(lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
        //lunch.isNestedScrollingEnabled = false

        snack.layoutManager = LinearLayoutManager(applicationContext)
        snack.adapter = ProductAdapter(snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
        //snack.isNestedScrollingEnabled = false

        dinner.layoutManager = LinearLayoutManager(applicationContext)
        dinner.adapter = ProductAdapter(dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)

        saveButton.setOnClickListener {


        }




    }
}