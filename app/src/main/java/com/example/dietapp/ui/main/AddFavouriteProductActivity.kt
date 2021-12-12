package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dietapp.R
import com.example.dietapp.objects.Functions

class AddFavouriteProductActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_favourite_product)
        Functions.saveFragment(applicationContext,"fav")

        val dbButton: Button = findViewById(R.id.add_favourite_product_add_base)
        val ownButton: Button = findViewById(R.id.add_favourite_product_add_own)


        dbButton.setOnClickListener {
            val intent = Intent(applicationContext, SearchDatabaseFavouriteProductsActivity::class.java)
            startActivity(intent)
        }

        ownButton.setOnClickListener {
            val intent = Intent(applicationContext, AddOwnFavouriteProductsActivity::class.java)
            startActivity(intent)
        }


    }
}