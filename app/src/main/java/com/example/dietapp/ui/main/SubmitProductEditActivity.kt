package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dietapp.R
import com.example.dietapp.dataclasses.FirebaseEditProduct
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class SubmitProductEditActivity : AppCompatActivity() {

    var meal: String = ""
    var id: String = ""
    var name: String = ""
    var proteins: Int = 0
    var fats: Int = 0
    var carbs: Int = 0
    var calories: Int = 0
    val uid = FirebaseAuth.getInstance().uid ?: ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_product_edit)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            name = bundle.getString("name").toString()
            id = bundle.getString("id").toString()
            proteins = bundle.getInt("proteins")
            fats = bundle.getInt("fats")
            carbs = bundle.getInt("carbs")
            calories = bundle.getInt("calories")
            meal = bundle.getString("meal").toString()
        }

        val subName: TextView = findViewById(R.id.submit_product_edit_name)
        val subProteins: TextView = findViewById(R.id.submit_product_edit_proteins)
        val subFats: TextView = findViewById(R.id.submit_product_edit_fats)
        val subCarbs: TextView = findViewById(R.id.submit_product_edit_carbs)
        val subCalories: TextView = findViewById(R.id.submit_product_edit_calories)
        val subMeal: TextView = findViewById(R.id.submit_product_edit_meal)
        val subWeight: EditText = findViewById(R.id.submit_product_edit_weight)
        val subAdd: Button = findViewById(R.id.submit_product_edit_add)



        subName.text = "Nazwa: $name"
        subProteins.text = "Białko: " + proteins.toString() + "g"
        subFats.text = "Tłuszcze: " + fats.toString() + "g"
        subCarbs.text = "Węglowodany: " + carbs.toString() + "g"
        subCalories.text = "Kalorie: " + calories.toString() + "kcal"
        subMeal.text = "Posiłek: " + Functions.getMealName(meal)

        subAdd.setOnClickListener {
            if(!subWeight.text.isNullOrEmpty()){

                addProductToMeal(subWeight.text.toString().toInt())

            }
            else{
                subWeight.error = "Waga jest wymagana!"
                subWeight.requestFocus()
            }
        }

    }

    private fun addProductToMeal(weight: Int) {

        // TU DODANIE DO BAZY
        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/userplans/$uid/$id/$meal")
        val productId = mealRef.push().key
        val product = FirebaseEditProduct(productId.toString(), name, calories, carbs, fats, proteins, weight,
            ServerValue.TIMESTAMP)

        mealRef.child(productId!!).setValue(product).addOnCompleteListener {

            println("udalo sieeeeee")
        }


        val intent = Intent(applicationContext, EditDietActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        intent.putExtra("id",id)
        startActivity(intent)

    }
}