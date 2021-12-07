package com.example.dietapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toDate
import com.example.dietapp.*
import com.example.dietapp.adapters.ProductAdapter
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class CurrentDayFragment:Fragment(R.layout.fragment_current_day) {

    var breakfastNameList = mutableListOf<String>()
    var breakfastWeightList = mutableListOf<Int>()
    var breakfastCaloriesList = mutableListOf<Int>()
    var breakfastEatenList = mutableListOf<Boolean>()

    private var secondBreakfastNameList = mutableListOf<String>()
    private var secondBreakfastWeightList = mutableListOf<Int>()
    private var secondBreakfastCaloriesList = mutableListOf<Int>()
    private var secondBreakfastEatenList = mutableListOf<Boolean>()

    private var lunchNameList = mutableListOf<String>()
    private var lunchWeightList = mutableListOf<Int>()
    private var lunchCaloriesList = mutableListOf<Int>()
    private var lunchEatenList = mutableListOf<Boolean>()

    private var snackNameList = mutableListOf<String>()
    private var snackWeightList = mutableListOf<Int>()
    private var snackCaloriesList = mutableListOf<Int>()
    private var snackEatenList = mutableListOf<Boolean>()

    private var dinnerNameList = mutableListOf<String>()
    private var dinnerWeightList = mutableListOf<Int>()
    private var dinnerCaloriesList = mutableListOf<Int>()
    private var dinnerEatenList = mutableListOf<Boolean>()



    private val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val breakfast: RecyclerView = requireView().findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = requireView().findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = requireView().findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = requireView().findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = requireView().findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        breakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        breakfast.adapter = ProductAdapter(breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
        //breakfast.isNestedScrollingEnabled = false

        secondBreakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        secondBreakfast.adapter = ProductAdapter(secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
        //secondBreakfast.isNestedScrollingEnabled = false

        lunch.layoutManager = LinearLayoutManager(activity?.applicationContext)
        lunch.adapter = ProductAdapter(lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
        //lunch.isNestedScrollingEnabled = false

        snack.layoutManager = LinearLayoutManager(activity?.applicationContext)
        snack.adapter = ProductAdapter(snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
        //snack.isNestedScrollingEnabled = false

        dinner.layoutManager = LinearLayoutManager(activity?.applicationContext)
        dinner.adapter = ProductAdapter(dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
        //dinner.isNestedScrollingEnabled = false

        getDailyMeals(Functions.getDate(requireContext())!!,uid,breakfast,secondBreakfast,lunch,snack,dinner)
        getDailyMeals(Functions.getDate(requireContext())!!,uid,breakfast,secondBreakfast,lunch,snack,dinner)

    }


    private fun addToBreakfastList(name: String, weight: Int, calories: Int, eaten: Boolean){
        breakfastNameList.add(name)
        breakfastWeightList.add(weight)
        breakfastCaloriesList.add(calories)
        breakfastEatenList.add(eaten)
    }

    private fun postBreakfastToList(){
        for(i in 1..5){
            addToBreakfastList("Produkt $i", 200,354, false)
        }

    }

    private fun addToSecondBreakfastList(name: String, weight: Int, calories: Int, eaten: Boolean){
        secondBreakfastNameList.add(name)
        secondBreakfastWeightList.add(weight)
        secondBreakfastCaloriesList.add(calories)
        secondBreakfastEatenList.add(eaten)
    }

    private fun postSecondBreakfastToList(){
        for(i in 1..5){
            addToSecondBreakfastList("Produkt $i", 300,128, false)
        }

    }

    private fun addToLunchList(name: String, weight: Int, calories: Int, eaten: Boolean){
        lunchNameList.add(name)
        lunchWeightList.add(weight)
        lunchCaloriesList.add(calories)
        lunchEatenList.add(eaten)
    }

    private fun postLunchToList(){
        for(i in 1..5){
            addToLunchList("Produkt $i", 250,608, false)
        }

    }

    private fun addToSnackList(name: String, weight: Int, calories: Int, eaten: Boolean){
        snackNameList.add(name)
        snackWeightList.add(weight)
        snackCaloriesList.add(calories)
        snackEatenList.add(eaten)
    }

    private fun postSnackToList(){
        for(i in 1..5){
            addToSnackList("Produkt $i", 150,561, false)
        }

    }

    private fun addToDinnerList(name: String, weight: Int, calories: Int, eaten: Boolean){
        dinnerNameList.add(name)
        dinnerWeightList.add(weight)
        dinnerCaloriesList.add(calories)
        dinnerEatenList.add(eaten)
    }

    private fun postDinnerToList(){
        for(i in 1..30){
            addToDinnerList("Produkt $i", 100,202, false)
        }

    }

    private fun clearBreakfast(){
        breakfastNameList.clear()
        breakfastWeightList.clear()
        breakfastCaloriesList.clear()
        breakfastEatenList.clear()
    }
    private fun clearSecondBreakfast(){
        secondBreakfastNameList.clear()
        secondBreakfastWeightList.clear()
        secondBreakfastCaloriesList.clear()
        secondBreakfastEatenList.clear()
    }
    private fun clearLunch(){
        lunchNameList.clear()
        lunchWeightList.clear()
        lunchCaloriesList.clear()
        lunchEatenList.clear()
    }
    private fun clearSnack(){
        snackNameList.clear()
        snackWeightList.clear()
        snackCaloriesList.clear()
        snackEatenList.clear()
    }
    private fun clearDinner(){
        dinnerNameList.clear()
        dinnerWeightList.clear()
        dinnerCaloriesList.clear()
        dinnerEatenList.clear()
    }



    private fun clearLists(){

        breakfastNameList.clear()
        breakfastWeightList.clear()
        breakfastCaloriesList.clear()
        breakfastEatenList.clear()
        secondBreakfastNameList.clear()
        secondBreakfastWeightList.clear()
        secondBreakfastCaloriesList.clear()
        secondBreakfastEatenList.clear()
        lunchNameList.clear()
        lunchWeightList.clear()
        lunchCaloriesList.clear()
        lunchEatenList.clear()
        snackNameList.clear()
        snackWeightList.clear()
        snackCaloriesList.clear()
        snackEatenList.clear()
        dinnerNameList.clear()
        dinnerWeightList.clear()
        dinnerCaloriesList.clear()
        dinnerEatenList.clear()

    }

    fun getDailyMeals(date: String, uid: String,breakfast: RecyclerView, secondBreakfast: RecyclerView, lunch: RecyclerView, snack: RecyclerView, dinner: RecyclerView){

        clearLists()

        println("/userdata/$uid/$date/breakfast")
        val database = FirebaseDatabase.getInstance()
        val breakfastRef = database.getReference("/userdata/$uid/$date/breakfast")
        val secondBreakfastRef = database.getReference("/userdata/$uid/$date/secondbreakfast")
        val lunchRef = database.getReference("/userdata/$uid/$date/lunch")
        val snackRef = database.getReference("/userdata/$uid/$date/snack")
        val dinnerRef = database.getReference("/userdata/$uid/$date/dinner")
        val breakfastProductsList = arrayListOf<FirebaseMealProduct>()
        val secondBreakfastProductsList = arrayListOf<FirebaseMealProduct>()
        val lunchProductsList = arrayListOf<FirebaseMealProduct>()
        val snackProductsList = arrayListOf<FirebaseMealProduct>()
        val dinnerProductsList = arrayListOf<FirebaseMealProduct>()

        breakfastRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearBreakfast()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        breakfastProductsList.add(product!!)
                        addToBreakfastList(productName.toString(),product.weight,product.calories,product.eaten)
                        println("w zapytaniu " + breakfastNameList.size)
                        breakfast.adapter = ProductAdapter(breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)


                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        secondBreakfastRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearSecondBreakfast()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        secondBreakfastProductsList.add(product!!)
                        addToSecondBreakfastList(productName.toString(),product.weight,product.calories,product.eaten)
                        secondBreakfast.adapter = ProductAdapter(secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)


                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        lunchRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearLunch()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        lunchProductsList.add(product!!)
                        addToLunchList(productName.toString(),product.weight,product.calories,product.eaten)
                        lunch.adapter = ProductAdapter(lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        snackRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearSnack()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        snackProductsList.add(product!!)
                        addToSnackList(productName.toString(),product.weight,product.calories,product.eaten)
                        snack.adapter = ProductAdapter(snackNameList, snackWeightList, snackCaloriesList, snackEatenList)

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        dinnerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearDinner()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        dinnerProductsList.add(product!!)
                        addToDinnerList(productName.toString(),product.weight,product.calories,product.eaten)
                        dinner.adapter = ProductAdapter(dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        breakfast.adapter?.notifyDataSetChanged()
        secondBreakfast.adapter?.notifyDataSetChanged()
        lunch.adapter?.notifyDataSetChanged()
        snack.adapter?.notifyDataSetChanged()
        dinner.adapter?.notifyDataSetChanged()
    }

    fun refreshMeals(){

        val breakfast: RecyclerView = requireView().findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = requireView().findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = requireView().findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = requireView().findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = requireView().findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        getDailyMeals(Functions.getDate(requireContext())!!,uid,breakfast,secondBreakfast,lunch,snack,dinner)



        println("tutaj")

    }


}