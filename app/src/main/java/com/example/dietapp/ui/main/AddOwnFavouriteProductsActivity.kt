package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.dietapp.R
import com.example.dietapp.databinding.ActivityAddOwnFavouriteProductsBinding
import com.example.dietapp.databinding.ActivityAddOwnProductsBinding
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.dataclasses.FavouriteProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class AddOwnFavouriteProductsActivity : AppCompatActivity() {

    var meal: String = ""
    private lateinit var binding: ActivityAddOwnFavouriteProductsBinding
    val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddOwnFavouriteProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
        }

        val name = binding.addOwnFavouriteProductName
        val calories = binding.addOwnFavouriteProductCalories
        val carbs = binding.addOwnFavouriteProductCarbs
        val fats = binding.addOwnFavouriteProductFats
        val proteins = binding.addOwnFavouriteProductProteins
        val addButton = binding.addOwnFavouriteProductAdd


        addButton.setOnClickListener {
            addOwnProduct()
        }

    }


    private fun addOwnProduct() {

        val name = binding.addOwnFavouriteProductName
        val nameText = binding.addOwnFavouriteProductName.text.toString().trim()
        val calories = binding.addOwnFavouriteProductCalories
        val carbs = binding.addOwnFavouriteProductCarbs
        val fats = binding.addOwnFavouriteProductFats
        val proteins = binding.addOwnFavouriteProductProteins

        if (nameText.isNullOrEmpty()) {
            name.error = "Nazwa jest wymagana!"
            name.requestFocus()
            return
        }
        if (calories.text.trim().isNullOrEmpty()) {
            calories.error = "Kaloryczność jest wymagana!"
            calories.requestFocus()
            return
        }
        if (calories.text.toString().toInt() <= 0) {
            calories.error = "Kaloryczność musi być dodatnia!"
            calories.requestFocus()
            return
        }
        if (carbs.text.trim().isNullOrEmpty()) {
            carbs.error = "Ilość węglowodanów jest wymagana!"
            carbs.requestFocus()
            return
        }
        if (carbs.text.toString().toInt() <= 0) {
            carbs.error = "Ilość węglowodanów musi być dodatnia!"
            carbs.requestFocus()
            return
        }
        if (fats.text.trim().isNullOrEmpty()) {
            fats.error = "Ilość tłuszczy jest wymagana!"
            fats.requestFocus()
            return
        }
        if (fats.text.toString().toInt() <= 0) {
            fats.error = "Ilość tłuszczy musi być dodatnia!"
            fats.requestFocus()
            return
        }
        if (proteins.text.trim().isNullOrEmpty()) {
            proteins.error = "Ilość białka jest wymagana!"
            proteins.requestFocus()
            return
        }
        if (proteins.text.toString().toInt() <= 0) {
            proteins.error = "Ilość białka musi być dodatnia!"
            proteins.requestFocus()
            return
        }

        val date = Functions.getDate(applicationContext).toString()
        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/userfavourites/$uid")
        val productId = mealRef.push().key
        val product = FavouriteProduct(productId.toString(), name.text.toString(), calories.text.toString().toInt(), carbs.text.toString().toInt(), fats.text.toString().toInt(), proteins.text.toString().toInt(),
            ServerValue.TIMESTAMP)

        mealRef.child(productId!!).setValue(product).addOnCompleteListener {

            println("udalo sieeeeee")
        }


        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("fragment","fav")
        intent.apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)



    }



}