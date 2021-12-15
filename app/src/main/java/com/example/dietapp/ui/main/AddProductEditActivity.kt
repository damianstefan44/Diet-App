package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dietapp.R
import com.example.dietapp.ui.login.ForgotPasswordActivity

class AddProductEditActivity : AppCompatActivity() {

    var meal: String = ""
    var planName: String = ""
    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_edit)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
            id = bundle.getString("id").toString()
            planName = bundle.getString("planName").toString()
        }

        val dbButton: Button = findViewById(R.id.add_product_edit_add_base)
        val ownButton: Button = findViewById(R.id.add_product_edit_add_own)
        val fromOwnButton: Button = findViewById(R.id.add_product_edit_add_from_own)


        dbButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchDatabaseProductsEditActivity::class.java)
            intent.putExtra("meal", meal)
            intent.putExtra("id", id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }

        fromOwnButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchOwnProductsEditActivity::class.java)
            intent.putExtra("meal", meal)
            intent.putExtra("id", id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }

        ownButton.setOnClickListener {
            val intent = Intent(applicationContext, AddOwnProductsEditActivity::class.java)
            intent.putExtra("meal", meal)
            intent.putExtra("id", id)
            intent.putExtra("planName",planName)
            startActivity(intent)
        }

    }
}