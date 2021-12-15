package com.example.dietapp.ui.main

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.cesarferreira.tempo.Tempo
import com.example.dietapp.R
import com.example.dietapp.adapters.ProductEditAdapter
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class LoadDietActivity : AppCompatActivity() {

    var date: String = ""
    var name: String = ""
    var calories: Int = 0
    var id: String = ""
    var currentDate: Date = Tempo.now
    val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_diet)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            name = bundle.getString("name").toString()
            calories = bundle.getInt("calories")
            id = bundle.getString("id").toString()
        }

        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val loadName: TextView = findViewById(R.id.load_diet_name)
        val loadCalories: TextView = findViewById(R.id.load_diet_calories)
        val loadDate: TextView = findViewById(R.id.load_diet_date)
        val loadButton: Button = findViewById(R.id.load_diet_add)

        loadName.text = "Nazwa: $name"
        loadCalories.text = "Kalorie: $calories"

        loadDate.setOnClickListener {

            val datePickerDialog = DatePickerDialog(this@LoadDietActivity, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->
                currentDate = Tempo.with(year,monthOfYear+1,dayOfMonth)
                date = Functions.databaseDate(currentDate)
                loadDate.text = Functions.getTextViewDate(currentDate)
            }, year, month, day)
            datePickerDialog.show()

        }

        loadButton.setOnClickListener {
            if(!loadDate.text.toString().trim().isNullOrEmpty()){
                loadData(date)
            }
            else{
                loadDate.error = "Data jest wymagana!"
                loadDate.requestFocus()
            }
        }

    }

    private fun loadData(date: String){

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

        val brRef = database.getReference("/userdata/$uid/$date/breakfast")
        val secRef = database.getReference("/userdata/$uid/$date/secondbreakfast")
        val lunRef = database.getReference("/userdata/$uid/$date/lunch")
        val snRef = database.getReference("/userdata/$uid/$date/snack")
        val dinRef = database.getReference("/userdata/$uid/$date/dinner")


        breakfastRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        breakfastProductsList.add(product!!)
                        val productId = brRef.push().key
                        val loadProduct = Product(productId.toString(), product.name,false, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        brRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        secondBreakfastRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        secondBreakfastProductsList.add(product!!)
                        val productId = secRef.push().key
                        val loadProduct = Product(productId.toString(), product.name,false, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        secRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        lunchRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        lunchProductsList.add(product!!)
                        val productId = lunRef.push().key
                        val loadProduct = Product(productId.toString(), product.name,false, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        lunRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        snackRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        snackProductsList.add(product!!)
                        val productId = snRef.push().key
                        val loadProduct = Product(productId.toString(), product.name,false, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        snRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        dinnerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        dinnerProductsList.add(product!!)
                        val productId = dinRef.push().key
                        val loadProduct = Product(productId.toString(), product.name,false, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        dinRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        finish()

    }
}