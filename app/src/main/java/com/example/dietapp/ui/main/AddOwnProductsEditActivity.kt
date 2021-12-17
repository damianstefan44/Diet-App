package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.dietapp.R
import com.example.dietapp.databinding.ActivityAddOwnProductsBinding
import com.example.dietapp.databinding.ActivityAddOwnProductsEditBinding
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.dataclasses.FirebaseEditProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class AddOwnProductsEditActivity : AppCompatActivity() {

    var meal: String = ""
    var id: String = ""
    var planName: String = ""
    private lateinit var binding: ActivityAddOwnProductsEditBinding
    val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddOwnProductsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
            id = bundle.getString("id").toString()
            planName = bundle.getString("planName").toString()
        }

        val addButton = binding.addOwnProductEditAdd


        addButton.setOnClickListener {
            addOwnProduct()
        }

    }

    private fun addOwnProduct() {

        val name = binding.addOwnProductEditName
        val nameText = binding.addOwnProductEditName.text.toString().trim()
        val calories = binding.addOwnProductEditCalories
        val carbs = binding.addOwnProductEditCarbs
        val fats = binding.addOwnProductEditFats
        val proteins = binding.addOwnProductEditProteins
        val weight = binding.addOwnProductEditWeight

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

        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/userplans/$uid/$id/$meal")
        val productId = mealRef.push().key
        val product = FirebaseEditProduct(productId.toString(), name.text.toString(), calories.text.toString().toInt(), carbs.text.toString().toInt(), fats.text.toString().toInt(), proteins.text.toString().toInt(), weight.text.toString().toInt(),
            ServerValue.TIMESTAMP)

        mealRef.child(productId!!).setValue(product).addOnCompleteListener {

            Log.d("TAG","Udało się dodać wartość do bazy")
        }


        val intent = Intent(applicationContext, EditDietActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.putExtra("id",id)
        intent.putExtra("planName",planName)
        startActivity(intent)



    }



}