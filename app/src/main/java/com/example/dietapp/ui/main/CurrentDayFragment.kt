package com.example.dietapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.toDate
import com.example.dietapp.*
import com.example.dietapp.adapters.ProductAdapter
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class CurrentDayFragment:Fragment(R.layout.fragment_current_day) {

    private var breakfastNameList = mutableListOf<String>()
    private var breakfastWeightList = mutableListOf<Int>()
    private var breakfastCaloriesList = mutableListOf<Int>()
    private var breakfastEatenList = mutableListOf<Boolean>()
    private var breakfastIdList = mutableListOf<String>()

    private var secondBreakfastNameList = mutableListOf<String>()
    private var secondBreakfastWeightList = mutableListOf<Int>()
    private var secondBreakfastCaloriesList = mutableListOf<Int>()
    private var secondBreakfastEatenList = mutableListOf<Boolean>()
    private var secondBreakfastIdList = mutableListOf<String>()

    private var lunchNameList = mutableListOf<String>()
    private var lunchWeightList = mutableListOf<Int>()
    private var lunchCaloriesList = mutableListOf<Int>()
    private var lunchEatenList = mutableListOf<Boolean>()
    private var lunchIdList = mutableListOf<String>()

    private var snackNameList = mutableListOf<String>()
    private var snackWeightList = mutableListOf<Int>()
    private var snackCaloriesList = mutableListOf<Int>()
    private var snackEatenList = mutableListOf<Boolean>()
    private var snackIdList = mutableListOf<String>()

    private var dinnerNameList = mutableListOf<String>()
    private var dinnerWeightList = mutableListOf<Int>()
    private var dinnerCaloriesList = mutableListOf<Int>()
    private var dinnerEatenList = mutableListOf<Boolean>()
    private var dinnerIdList = mutableListOf<String>()

    var breakfastCalories: TextView? = null
    var secondBreakfastCalories: TextView? = null
    var lunchCalories: TextView? = null
    var snackCalories: TextView? = null
    var dinnerCalories: TextView? = null


    private val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Functions.saveFragment(requireContext(),"cur")
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val ctx = requireActivity().applicationContext

        val breakfast: RecyclerView = requireView().findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = requireView().findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = requireView().findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = requireView().findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = requireView().findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        val breakfastAdd: ImageView = requireView().findViewById<View>(R.id.breakfastAdd) as ImageView
        val secondBreakfastAdd: ImageView = requireView().findViewById<View>(R.id.secondBreakfastAdd) as ImageView
        val lunchAdd: ImageView = requireView().findViewById<View>(R.id.lunchAdd) as ImageView
        val snackAdd: ImageView = requireView().findViewById<View>(R.id.snackAdd) as ImageView
        val dinnerAdd: ImageView = requireView().findViewById<View>(R.id.dinnerAdd) as ImageView

        breakfastCalories = requireView().findViewById<View>(R.id.breakfastCalories) as TextView
        secondBreakfastCalories = requireView().findViewById<View>(R.id.secondBreakfastCalories) as TextView
        lunchCalories = requireView().findViewById<View>(R.id.lunchCalories) as TextView
        snackCalories = requireView().findViewById<View>(R.id.snackCalories) as TextView
        dinnerCalories = requireView().findViewById<View>(R.id.dinnerCalories) as TextView


        breakfastAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            val meal = "breakfast"
            intent.putExtra("meal",meal)
            startActivity(intent)
        }
        secondBreakfastAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            val meal = "secondbreakfast"
            intent.putExtra("meal",meal)
            startActivity(intent)
        }
        lunchAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            val meal = "lunch"
            intent.putExtra("meal",meal)
            startActivity(intent)
        }
        snackAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            val meal = "snack"
            intent.putExtra("meal",meal)
            startActivity(intent)
        }
        dinnerAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            val meal = "dinner"
            intent.putExtra("meal",meal)
            startActivity(intent)
        }



        breakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        breakfast.adapter = ProductAdapter(ctx,"breakfast", breakfastIdList, breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
        //breakfast.isNestedScrollingEnabled = false

        secondBreakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        secondBreakfast.adapter = ProductAdapter(ctx,"secondbreakfast", secondBreakfastIdList, secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
        //secondBreakfast.isNestedScrollingEnabled = false

        lunch.layoutManager = LinearLayoutManager(activity?.applicationContext)
        lunch.adapter = ProductAdapter(ctx,"lunch", lunchIdList, lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
        //lunch.isNestedScrollingEnabled = false

        snack.layoutManager = LinearLayoutManager(activity?.applicationContext)
        snack.adapter = ProductAdapter(ctx,"snack", snackIdList, snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
        //snack.isNestedScrollingEnabled = false

        dinner.layoutManager = LinearLayoutManager(activity?.applicationContext)
        dinner.adapter = ProductAdapter(ctx,"dinner", dinnerIdList, dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
        //dinner.isNestedScrollingEnabled = false

        getDailyMeals(ctx,Functions.getDate(requireContext())!!,uid,breakfast,secondBreakfast,lunch,snack,dinner)

    }

    private fun addToBreakfastList(name: String, weight: Int, calories: Int, eaten: Boolean, id: String){
        breakfastNameList.add(name)
        breakfastWeightList.add(weight)
        breakfastCaloriesList.add(calories)
        breakfastEatenList.add(eaten)
        breakfastIdList.add(id)
    }

    private fun addToSecondBreakfastList(name: String, weight: Int, calories: Int, eaten: Boolean, id: String){
        secondBreakfastNameList.add(name)
        secondBreakfastWeightList.add(weight)
        secondBreakfastCaloriesList.add(calories)
        secondBreakfastEatenList.add(eaten)
        secondBreakfastIdList.add(id)
    }

    private fun addToLunchList(name: String, weight: Int, calories: Int, eaten: Boolean, id: String){
        lunchNameList.add(name)
        lunchWeightList.add(weight)
        lunchCaloriesList.add(calories)
        lunchEatenList.add(eaten)
        lunchIdList.add(id)
    }

    private fun addToSnackList(name: String, weight: Int, calories: Int, eaten: Boolean, id: String){
        snackNameList.add(name)
        snackWeightList.add(weight)
        snackCaloriesList.add(calories)
        snackEatenList.add(eaten)
        snackIdList.add(id)
    }

    private fun addToDinnerList(name: String, weight: Int, calories: Int, eaten: Boolean, id: String){
        dinnerNameList.add(name)
        dinnerWeightList.add(weight)
        dinnerCaloriesList.add(calories)
        dinnerEatenList.add(eaten)
        dinnerIdList.add(id)
    }

    private fun clearBreakfast(){
        breakfastNameList.clear()
        breakfastWeightList.clear()
        breakfastCaloriesList.clear()
        breakfastEatenList.clear()
        breakfastIdList.clear()
    }
    private fun clearSecondBreakfast(){
        secondBreakfastNameList.clear()
        secondBreakfastWeightList.clear()
        secondBreakfastCaloriesList.clear()
        secondBreakfastEatenList.clear()
        secondBreakfastIdList.clear()
    }
    private fun clearLunch(){
        lunchNameList.clear()
        lunchWeightList.clear()
        lunchCaloriesList.clear()
        lunchEatenList.clear()
        lunchIdList.clear()
    }
    private fun clearSnack(){
        snackNameList.clear()
        snackWeightList.clear()
        snackCaloriesList.clear()
        snackEatenList.clear()
        snackIdList.clear()
    }
    private fun clearDinner(){
        dinnerNameList.clear()
        dinnerWeightList.clear()
        dinnerCaloriesList.clear()
        dinnerEatenList.clear()
        dinnerIdList.clear()
    }

    private fun clearLists(){

        breakfastNameList.clear()
        breakfastWeightList.clear()
        breakfastCaloriesList.clear()
        breakfastEatenList.clear()
        breakfastIdList.clear()
        secondBreakfastNameList.clear()
        secondBreakfastWeightList.clear()
        secondBreakfastCaloriesList.clear()
        secondBreakfastEatenList.clear()
        secondBreakfastIdList.clear()
        lunchNameList.clear()
        lunchWeightList.clear()
        lunchCaloriesList.clear()
        lunchEatenList.clear()
        lunchIdList.clear()
        snackNameList.clear()
        snackWeightList.clear()
        snackCaloriesList.clear()
        snackEatenList.clear()
        snackIdList.clear()
        dinnerNameList.clear()
        dinnerWeightList.clear()
        dinnerCaloriesList.clear()
        dinnerEatenList.clear()
        dinnerIdList.clear()

    }

    fun getDailyMeals(ctx: Context, date: String, uid: String,breakfast: RecyclerView, secondBreakfast: RecyclerView, lunch: RecyclerView, snack: RecyclerView, dinner: RecyclerView){

        clearLists()

        println("/userdata/$uid/$date/breakfast")
        val database = FirebaseDatabase.getInstance()
        val breakfastRef = database.getReference("/userdata/$uid/$date/breakfast").orderByChild("timestamp")
        val secondBreakfastRef = database.getReference("/userdata/$uid/$date/secondbreakfast").orderByChild("timestamp")
        val lunchRef = database.getReference("/userdata/$uid/$date/lunch").orderByChild("timestamp")
        val snackRef = database.getReference("/userdata/$uid/$date/snack").orderByChild("timestamp")
        val dinnerRef = database.getReference("/userdata/$uid/$date/dinner").orderByChild("timestamp")
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
                        addToBreakfastList(product.name,product.weight,product.calories,product.eaten, product.id)
                        breakfast.adapter = ProductAdapter(ctx,"breakfast", breakfastIdList, breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
                        refreshCaloriesCounters("breakfast", breakfastWeightList, breakfastCaloriesList, breakfastEatenList)

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
                        addToSecondBreakfastList(product.name,product.weight,product.calories,product.eaten, product.id)
                        secondBreakfast.adapter = ProductAdapter(ctx,"secondbreakfast", secondBreakfastIdList, secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
                        refreshCaloriesCounters("secondbreakfast", secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
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
                        addToLunchList(product.name,product.weight,product.calories,product.eaten, product.id)
                        lunch.adapter = ProductAdapter(ctx,"lunch", lunchIdList, lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
                        refreshCaloriesCounters("lunch", lunchWeightList, lunchCaloriesList, lunchEatenList)
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
                        addToSnackList(product.name,product.weight,product.calories,product.eaten, product.id)
                        snack.adapter = ProductAdapter(ctx,"snack", snackIdList, snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
                        refreshCaloriesCounters("snack", snackWeightList, snackCaloriesList, snackEatenList)
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
                        addToDinnerList(product.name,product.weight,product.calories,product.eaten, product.id)
                        dinner.adapter = ProductAdapter(ctx,"dinner", dinnerIdList, dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
                        refreshCaloriesCounters("dinner", dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
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

    fun refreshMeals(ctx: Context){

        val breakfast: RecyclerView = requireView().findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = requireView().findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = requireView().findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = requireView().findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = requireView().findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        getDailyMeals(ctx, Functions.getDate(requireContext())!!,uid,breakfast,secondBreakfast,lunch,snack,dinner)

    }


    @SuppressLint("SetTextI18n")
    private fun refreshCaloriesCounters(mealType: String, mealWeightList: MutableList<Int>, mealCaloriesList: MutableList<Int>, mealEatenList: MutableList<Boolean>) {

        var mealCaloriesCounter: Int = 0
        var size: Int = 0
        size = mealCaloriesList.size - 1

        for(i in 0..size){
            if(mealEatenList[i]){
                mealCaloriesCounter += (mealCaloriesList[i] * mealWeightList[i] / 100)
            }
        }

        when (mealType) {
            "breakfast" -> breakfastCalories!!.text = "$mealCaloriesCounter kcal"
            "secondbreakfast" -> secondBreakfastCalories!!.text = "$mealCaloriesCounter kcal"
            "lunch" -> lunchCalories!!.text = "$mealCaloriesCounter kcal"
            "snack" -> snackCalories!!.text = "$mealCaloriesCounter kcal"
            "dinner" -> dinnerCalories!!.text = "$mealCaloriesCounter kcal"
            else -> {}

        }

    }


}