package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dietapp.R
import com.example.dietapp.databinding.ActivityAddOwnProductsBinding
import com.example.dietapp.databinding.ActivityAdminAddProductBinding
import com.example.dietapp.dataclasses.AdminProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class AdminAddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addButton = binding.adminAddProductAdd


        addButton.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {

        val name = binding.adminAddProductName
        val nameText = binding.adminAddProductName.text.toString().trim()
        val calories = binding.adminAddProductCalories
        val carbs = binding.adminAddProductCarbs
        val fats = binding.adminAddProductFats
        val proteins = binding.adminAddProductProteins

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

        val date = Functions.getDate(applicationContext).toString()
        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/products")
        val productId = mealRef.push().key
        val product = AdminProduct(name.text.toString().lowercase(), calories.text.toString().toInt(), carbs.text.toString().toInt(), fats.text.toString().toInt(), proteins.text.toString().toInt(),
            ServerValue.TIMESTAMP)

        mealRef.child(productId!!).setValue(product)

            .addOnSuccessListener {
                Toast.makeText(applicationContext,"Udało się dodać produkt", Toast.LENGTH_LONG).show()
                Log.d("TAG","Udało się dodać wartość produkt do bazy")
                name.text.clear()
                calories.text.clear()
                carbs.text.clear()
                fats.text.clear()
                proteins.text.clear()
                name.requestFocus()
            }
            .addOnFailureListener {
                Log.d("TAG","Nie udało się dodać wartości do bazy")
            }




    }
}