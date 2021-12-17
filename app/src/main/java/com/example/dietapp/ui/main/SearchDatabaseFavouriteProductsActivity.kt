package com.example.dietapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.LimitedSearchedFavouriteProductAdapter
import com.example.dietapp.adapters.LimitedSearchedProductAdapter
import com.example.dietapp.adapters.SearchedProductAdapter
import com.example.dietapp.dataclasses.FirebaseProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchDatabaseFavouriteProductsActivity : AppCompatActivity() {

    var meal: String = ""
    var productAdapter: LimitedSearchedFavouriteProductAdapter? = null
    var recycler: RecyclerView? = null
    var searchEditText: EditText? = null
    private var nameList = mutableListOf<String>()
    private var proteinsList = mutableListOf<Int>()
    private var fatsList = mutableListOf<Int>()
    private var carbsList = mutableListOf<Int>()
    private var caloriesList = mutableListOf<Int>()
    private var productList = mutableListOf<FirebaseProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_database_favourite_products)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
        }

        val recycler = findViewById<RecyclerView>(R.id.search_database_favourite_products_recycler)
        val searchEditText = findViewById<EditText>(R.id.search_database_favourite_products_search)
        val search = findViewById<ImageView>(R.id.search_database_favourite_products_search_button)


        retrieveProducts(searchEditText)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext)


        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearList()
                productAdapter = LimitedSearchedFavouriteProductAdapter(applicationContext, nameList, proteinsList, fatsList, carbsList, caloriesList)
                recycler!!.adapter = productAdapter
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        search.setOnClickListener {
            val s = searchEditText.text.toString()
            searchForProducts(s,recycler)
        }

    }

    private fun addToList(name: String, proteins: Int, fats: Int, carbs: Int, calories: Int){
        nameList.add(name)
        proteinsList.add(proteins)
        fatsList.add(fats)
        carbsList.add(carbs)
        caloriesList.add(calories)
    }

    private fun clearList(){
        nameList.clear()
        proteinsList.clear()
        fatsList.clear()
        carbsList.clear()
        caloriesList.clear()
    }

    private fun retrieveProducts(searchText: EditText) {

        val database = FirebaseDatabase.getInstance()
        val productsRef = database.getReference("/products")

        productsRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    if(searchText.text.equals("")){
                        for(productSnapshot in dataSnapshot.children){
                            val productName = productSnapshot.key
                            val product = productSnapshot.getValue(FirebaseProduct::class.java)
                            productList.add(product!!)
                            addToList(product.name,product.proteins,product.fats,product.carbs,product.calories)


                        }
                        productAdapter = LimitedSearchedFavouriteProductAdapter(applicationContext, nameList, proteinsList, fatsList, carbsList, caloriesList)
                        recycler!!.adapter = productAdapter

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.d("TAG","The read failed: " + databaseError.code)

            }

        })

    }

    private fun searchForProducts(str: String, recycler: RecyclerView){

        val database = FirebaseDatabase.getInstance()
        val productsQuery = database.reference.child("products").orderByChild("name").startAt(str).endAt(str + "\uf8ff")

        productsQuery.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseProduct::class.java)
                        productList.add(product!!)
                        addToList(product.name,product.proteins,product.fats,product.carbs,product.calories)

                    }
                    productAdapter = LimitedSearchedFavouriteProductAdapter(applicationContext, nameList, proteinsList, fatsList, carbsList, caloriesList)
                    recycler.adapter = productAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG","The read failed: " + databaseError.code)
            }

        })

    }



}