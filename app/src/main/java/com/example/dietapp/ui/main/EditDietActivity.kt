package com.example.dietapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.ProductAdapter
import com.example.dietapp.adapters.ProductEditAdapter
import com.example.dietapp.dataclasses.DietValues
import com.example.dietapp.dataclasses.FavouriteProduct
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditDietActivity : AppCompatActivity() {

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

    var totalCalories: Int = 0

    var id: String = ""
    var planName: String = ""
    val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_diet)

        Functions.saveFragment(applicationContext,"diets")

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            id = bundle.getString("id").toString()
            planName = bundle.getString("planName").toString()
        }
        id = intent.getStringExtra("id").toString()
        planName = intent.getStringExtra("planName").toString()

        if(id == "" || id == "false"){
            val database = FirebaseDatabase.getInstance()
            val dietRef = database.getReference("/userplans/$uid")
            id = dietRef.push().key.toString()
        }

        if(planName != "" && !planName.isNullOrEmpty()){
            val database = FirebaseDatabase.getInstance()
            val dietRef = database.getReference("/userplans/$uid/$id")

            val dietValues = DietValues(id, planName, 0,
                ServerValue.TIMESTAMP)

            dietRef.child("values").setValue(dietValues)

        }


        val breakfast: RecyclerView = findViewById<View>(R.id.edit_breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = findViewById<View>(R.id.edit_secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = findViewById<View>(R.id.edit_lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = findViewById<View>(R.id.edit_snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = findViewById<View>(R.id.edit_dinnerRecyclerView) as RecyclerView

        val breakfastAdd: ImageView = findViewById<View>(R.id.edit_breakfastAdd) as ImageView
        val secondBreakfastAdd: ImageView = findViewById<View>(R.id.edit_secondBreakfastAdd) as ImageView
        val lunchAdd: ImageView = findViewById<View>(R.id.edit_lunchAdd) as ImageView
        val snackAdd: ImageView = findViewById<View>(R.id.edit_snackAdd) as ImageView
        val dinnerAdd: ImageView = findViewById<View>(R.id.edit_dinnerAdd) as ImageView

        breakfastCalories = findViewById<View>(R.id.edit_breakfastCalories) as TextView
        secondBreakfastCalories = findViewById<View>(R.id.edit_secondBreakfastCalories) as TextView
        lunchCalories = findViewById<View>(R.id.edit_lunchCalories) as TextView
        snackCalories = findViewById<View>(R.id.edit_snackCalories) as TextView
        dinnerCalories = findViewById<View>(R.id.edit_dinnerCalories) as TextView

        val saveButton: Button = findViewById(R.id.edit_save_button)

        breakfastAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddProductEditActivity::class.java)
            val meal = "breakfast"
            intent.putExtra("meal",meal)
            intent.putExtra("id",id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }
        secondBreakfastAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddProductEditActivity::class.java)
            val meal = "secondbreakfast"
            intent.putExtra("meal",meal)
            intent.putExtra("id",id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }
        lunchAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddProductEditActivity::class.java)
            val meal = "lunch"
            intent.putExtra("meal",meal)
            intent.putExtra("id",id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }
        snackAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddProductEditActivity::class.java)
            val meal = "snack"
            intent.putExtra("meal",meal)
            intent.putExtra("id",id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }
        dinnerAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddProductEditActivity::class.java)
            val meal = "dinner"
            intent.putExtra("meal",meal)
            intent.putExtra("id",id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }

        breakfast.layoutManager = LinearLayoutManager(applicationContext)
        breakfast.adapter = ProductEditAdapter(applicationContext,"breakfast", id, breakfastIdList, breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
        //breakfast.isNestedScrollingEnabled = false

        secondBreakfast.layoutManager = LinearLayoutManager(applicationContext)
        secondBreakfast.adapter = ProductEditAdapter(applicationContext, "secondbreakfast", id, secondBreakfastIdList, secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
        //secondBreakfast.isNestedScrollingEnabled = false

        lunch.layoutManager = LinearLayoutManager(applicationContext)
        lunch.adapter = ProductEditAdapter(applicationContext, "lunch", id, lunchIdList, lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
        //lunch.isNestedScrollingEnabled = false

        snack.layoutManager = LinearLayoutManager(applicationContext)
        snack.adapter = ProductEditAdapter(applicationContext, "snack", id, snackIdList, snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
        //snack.isNestedScrollingEnabled = false

        dinner.layoutManager = LinearLayoutManager(applicationContext)
        dinner.adapter = ProductEditAdapter(applicationContext, "dinner", id, dinnerIdList, dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)

        getDailyMeals(applicationContext,
            uid,breakfast,secondBreakfast,lunch,snack,dinner)

        saveButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




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

    fun getDailyMeals(ctx: Context, uid: String, breakfast: RecyclerView, secondBreakfast: RecyclerView, lunch: RecyclerView, snack: RecyclerView, dinner: RecyclerView){

        clearLists()

        val database = FirebaseDatabase.getInstance()
        val breakfastRef = database.getReference("/userplans/$uid/$id/breakfast").orderByChild("timestamp")
        val secondBreakfastRef = database.getReference("/userplans/$uid/$id/secondbreakfast").orderByChild("timestamp")
        val lunchRef = database.getReference("/userplans/$uid/$id/lunch").orderByChild("timestamp")
        val snackRef = database.getReference("/userplans/$uid/$id/snack").orderByChild("timestamp")
        val dinnerRef = database.getReference("/userplans/$uid/$id/dinner").orderByChild("timestamp")
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
                        breakfast.adapter = ProductEditAdapter(ctx,"breakfast", id, breakfastIdList, breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
                        refreshCaloriesCounters("breakfast", breakfastWeightList, breakfastCaloriesList)
                        getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)

                    }
                }
                else{
                    refreshCaloriesCounters("breakfast", breakfastWeightList, breakfastCaloriesList)
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
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
                        secondBreakfast.adapter = ProductEditAdapter(ctx,"secondbreakfast", id, secondBreakfastIdList, secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
                        refreshCaloriesCounters("secondbreakfast", secondBreakfastWeightList, secondBreakfastCaloriesList)
                        getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                    }
                }
                else{
                    refreshCaloriesCounters("secondbreakfast", secondBreakfastWeightList, secondBreakfastCaloriesList)
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
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
                        lunch.adapter = ProductEditAdapter(ctx,"lunch", id, lunchIdList, lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
                        refreshCaloriesCounters("lunch", lunchWeightList, lunchCaloriesList)
                        getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                    }
                }
                else{
                    refreshCaloriesCounters("lunch", lunchWeightList, lunchCaloriesList)
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
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
                        snack.adapter = ProductEditAdapter(ctx,"snack", id, snackIdList, snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
                        refreshCaloriesCounters("snack", snackWeightList, snackCaloriesList)
                        getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                    }
                }
                else{
                    refreshCaloriesCounters("snack", snackWeightList, snackCaloriesList)
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
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
                        dinner.adapter = ProductEditAdapter(ctx,"dinner", id, dinnerIdList, dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
                        refreshCaloriesCounters("dinner", dinnerWeightList, dinnerCaloriesList)
                        getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                    }
                }
                else{
                    refreshCaloriesCounters("dinner", dinnerWeightList, dinnerCaloriesList)
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
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
        refreshCaloriesCounters("dinner", dinnerWeightList, dinnerCaloriesList)
        refreshCaloriesCounters("secondbreakfast", secondBreakfastWeightList, secondBreakfastCaloriesList)
        refreshCaloriesCounters("lunch", lunchWeightList, lunchCaloriesList)
        refreshCaloriesCounters("snack", snackWeightList, snackCaloriesList)
        refreshCaloriesCounters("dinner", dinnerWeightList, dinnerCaloriesList)
    }

    fun refreshMeals(ctx: Context){

        val breakfast: RecyclerView = findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        getDailyMeals(ctx,uid,breakfast,secondBreakfast,lunch,snack,dinner)

    }

    @SuppressLint("SetTextI18n")
    private fun refreshCaloriesCounters(mealType: String, mealWeightList: MutableList<Int>, mealCaloriesList: MutableList<Int>) {

        var mealCaloriesCounter: Int = 0
        var size: Int = 0
        size = mealCaloriesList.size - 1

        if(size == -1)
            mealCaloriesCounter = 0
        else{
            for(i in 0..size){
                mealCaloriesCounter += (mealCaloriesList[i] * mealWeightList[i] / 100)
            }
        }
        println("0000000000000000000000000000000000000000000000000000")
        println(mealCaloriesCounter)
        println("0000000000000000000000000000000000000000000000000000")


        when (mealType) {
            "breakfast" -> breakfastCalories!!.text = "$mealCaloriesCounter kcal"
            "secondbreakfast" -> secondBreakfastCalories!!.text = "$mealCaloriesCounter kcal"
            "lunch" -> lunchCalories!!.text = "$mealCaloriesCounter kcal"
            "snack" -> snackCalories!!.text = "$mealCaloriesCounter kcal"
            "dinner" -> dinnerCalories!!.text = "$mealCaloriesCounter kcal"
            else -> {}

        }

    }

    private fun getCalories(breakfastWeightList: MutableList<Int>, breakfastCaloriesList: MutableList<Int>,
                            secondBreakfastWeightList: MutableList<Int>, secondBreakfastCaloriesList: MutableList<Int>,
                            lunchWeightList: MutableList<Int>, lunchCaloriesList: MutableList<Int>,
                            snackWeightList: MutableList<Int>, snackCaloriesList: MutableList<Int>,
                            dinnerWeightList: MutableList<Int>, dinnerCaloriesList: MutableList<Int>){

        var breakfastCaloriesCounter: Int = 0
        var secondBreakfastCaloriesCounter: Int = 0
        var lunchCaloriesCounter: Int = 0
        var snackCaloriesCounter: Int = 0
        var dinnerCaloriesCounter: Int = 0
        var size: Int = 0

        size = breakfastCaloriesList.size - 1
        for(i in 0..size){
            breakfastCaloriesCounter += (breakfastCaloriesList[i] * breakfastWeightList[i] / 100)
        }
        size = secondBreakfastCaloriesList.size - 1
        for(i in 0..size){
            secondBreakfastCaloriesCounter += (secondBreakfastCaloriesList[i] * secondBreakfastWeightList[i] / 100)
        }
        size = lunchCaloriesList.size - 1
        for(i in 0..size){
            lunchCaloriesCounter += (lunchCaloriesList[i] * lunchWeightList[i] / 100)
        }
        size = snackCaloriesList.size - 1
        for(i in 0..size){
            snackCaloriesCounter += (snackCaloriesList[i] * snackWeightList[i] / 100)
        }
        size = dinnerCaloriesList.size - 1
        for(i in 0..size){
            dinnerCaloriesCounter += (dinnerCaloriesList[i] * dinnerWeightList[i] / 100)
        }

        val database = FirebaseDatabase.getInstance()
        val dietRef = database.getReference("/userplans/$uid/$id/values")

        val dietCalories = breakfastCaloriesCounter + secondBreakfastCaloriesCounter + lunchCaloriesCounter + snackCaloriesCounter + dinnerCaloriesCounter

        dietRef.child("calories").setValue(dietCalories)


    }

}