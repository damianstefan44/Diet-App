package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.dietapp.R
import com.example.dietapp.databinding.ActivityAddOwnProductsBinding
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class AddOwnProductsActivity : AppCompatActivity() {

    var meal: String = ""
    private lateinit var binding: ActivityAddOwnProductsBinding
    val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddOwnProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
        }

        val addButton = binding.addOwnProductAdd

        addButton.setOnClickListener {
            addOwnProduct()
        }

    }

    private fun addOwnProduct() {

        val name = binding.addOwnProductName
        val nameText = binding.addOwnProductName.text.toString().trim()
        val calories = binding.addOwnProductCalories
        val carbs = binding.addOwnProductCarbs
        val fats = binding.addOwnProductFats
        val proteins = binding.addOwnProductProteins
        val weight = binding.addOwnProductWeight

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
        if (calories.text.toString().toInt() < 0) {
            calories.error = "Kaloryczność musi być dodatnia!"
            calories.requestFocus()
            return
        }
        if (carbs.text.trim().isNullOrEmpty()) {
            carbs.error = "Ilość węglowodanów jest wymagana!"
            carbs.requestFocus()
            return
        }
        if (carbs.text.toString().toInt() < 0) {
            carbs.error = "Ilość węglowodanów musi być dodatnia!"
            carbs.requestFocus()
            return
        }
        if (fats.text.trim().isNullOrEmpty()) {
            fats.error = "Ilość tłuszczy jest wymagana!"
            fats.requestFocus()
            return
        }
        if (fats.text.toString().toInt() < 0) {
            fats.error = "Ilość tłuszczy musi być dodatnia!"
            fats.requestFocus()
            return
        }
        if (proteins.text.trim().isNullOrEmpty()) {
            proteins.error = "Ilość białka jest wymagana!"
            proteins.requestFocus()
            return
        }
        if (proteins.text.toString().toInt() < 0) {
            proteins.error = "Ilość białka musi być dodatnia!"
            proteins.requestFocus()
            return
        }
        if (weight.text.trim().isNullOrEmpty()) {
            weight.error = "Waga jest wymagana!"
            weight.requestFocus()
            return
        }
        if (weight.text.toString().toInt() <= 0) {
            weight.error = "Waga musi być dodatnia!"
            weight.requestFocus()
            return
        }

        val date = Functions.getDate(applicationContext).toString()
        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/userdata/$uid/$date/$meal")
        val productId = mealRef.push().key
        val product = Product(productId.toString(), name.text.toString(),false, calories.text.toString().toInt(), carbs.text.toString().toInt(), fats.text.toString().toInt(), proteins.text.toString().toInt(), weight.text.toString().toInt(),
            ServerValue.TIMESTAMP)

        mealRef.child(productId!!).setValue(product)
            .addOnSuccessListener {
                Log.d("TAG","Udało się dodać wartość do bazy")
            }
            .addOnFailureListener {
                Log.d("TAG","Nie udało się dodać wartości do bazy")
            }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)



    }



}