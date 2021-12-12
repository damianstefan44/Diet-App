package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dietapp.R
import com.example.dietapp.dataclasses.FavouriteProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class SubmitFavouriteProductActivity : AppCompatActivity() {

    var name: String = ""
    var proteins: Int = 0
    var fats: Int = 0
    var carbs: Int = 0
    var calories: Int = 0
    val uid = FirebaseAuth.getInstance().uid ?: ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_favourite_product)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            name = bundle.getString("name").toString()
            proteins = bundle.getInt("proteins")
            fats = bundle.getInt("fats")
            carbs = bundle.getInt("carbs")
            calories = bundle.getInt("calories")
        }

        val subName: TextView = findViewById(R.id.submit_favourite_product_name)
        val subProteins: TextView = findViewById(R.id.submit_favourite_product_proteins)
        val subFats: TextView = findViewById(R.id.submit_favourite_product_fats)
        val subCarbs: TextView = findViewById(R.id.submit_favourite_product_carbs)
        val subCalories: TextView = findViewById(R.id.submit_favourite_product_calories)
        val subAdd: Button = findViewById(R.id.submit_favourite_product_add)


        subName.text = "Nazwa: $name"
        subProteins.text = "Białko: " + proteins.toString() + "g"
        subFats.text = "Tłuszcze: " + fats.toString() + "g"
        subCarbs.text = "Węglowodany: " + carbs.toString() + "g"
        subCalories.text = "Kalorie: " + calories.toString() + "kcal"

        subAdd.setOnClickListener {
            addProductToMeal()
        }

    }

    private fun addProductToMeal() {

        // TU DODANIE DO BAZY
        val database = FirebaseDatabase.getInstance()
        val favRef = database.getReference("/userfavourites/$uid")
        val productId = favRef.push().key
        val product = FavouriteProduct(productId.toString(), name, calories, carbs, fats, proteins,
            ServerValue.TIMESTAMP)

        favRef.child(productId!!).setValue(product).addOnCompleteListener {

            println("udalo sieeeeee")
        }

        Functions.saveFragment(applicationContext,"fav")
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)

    }
}