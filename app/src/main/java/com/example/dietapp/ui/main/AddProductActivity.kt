package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dietapp.R
import com.example.dietapp.ui.login.ForgotPasswordActivity

class AddProductActivity : AppCompatActivity() {

    var meal: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
        }

        val dbButton: Button = findViewById(R.id.add_product_add_base)
        val ownButton: Button = findViewById(R.id.add_product_add_own)
        val fromOwnButton: Button = findViewById(R.id.add_product_add_from_own)


        dbButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchDatabaseProductsActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }

        ownButton.setOnClickListener {
            val intent = Intent(applicationContext, AddOwnProductsActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }

        fromOwnButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchOwnProductsActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }

    }
}