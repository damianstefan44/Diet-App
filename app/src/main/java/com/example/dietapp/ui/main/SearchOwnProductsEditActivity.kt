package com.example.dietapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.LimitedSearchedFavouriteProductEditAdapter
import com.example.dietapp.adapters.SearchedProductAdapter
import com.example.dietapp.dataclasses.FavouriteEditProduct
import com.example.dietapp.dataclasses.FirebaseProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchOwnProductsEditActivity : AppCompatActivity() {

    var meal: String = ""
    var id: String = ""
    var productAdapter: LimitedSearchedFavouriteProductEditAdapter? = null
    var recycler: RecyclerView? = null
    var searchEditText: EditText? = null
    private var nameList = mutableListOf<String>()
    private var proteinsList = mutableListOf<Int>()
    private var fatsList = mutableListOf<Int>()
    private var carbsList = mutableListOf<Int>()
    private var caloriesList = mutableListOf<Int>()
    private var idsList = mutableListOf<String>()
    private var productList = mutableListOf<FavouriteEditProduct>()
    var uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_own_products_edit)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            meal = bundle.getString("meal").toString()
            id = bundle.getString("id").toString()
        }

        val recycler = findViewById<RecyclerView>(R.id.search_own_products_edit_recycler)
        val searchEditText = findViewById<EditText>(R.id.search_own_products_edit_search)
        retrieveProducts(searchEditText)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext)

        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchForProducts(s.toString().lowercase(),recycler)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun addToList(name: String, proteins: Int, fats: Int, carbs: Int, calories: Int, id: String){
        nameList.add(name)
        proteinsList.add(proteins)
        fatsList.add(fats)
        carbsList.add(carbs)
        caloriesList.add(calories)
        idsList.add(id)
    }

    private fun clearList(){
        nameList.clear()
        proteinsList.clear()
        fatsList.clear()
        carbsList.clear()
        caloriesList.clear()
        idsList.clear()
    }

    private fun retrieveProducts(searchText: EditText) {

        val database = FirebaseDatabase.getInstance()
        val productsRef = database.getReference("/userfavourites/$uid")

        productsRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    if(searchText.text.equals("")){
                        for(productSnapshot in dataSnapshot.children){
                            val productName = productSnapshot.key
                            val product = productSnapshot.getValue(FavouriteEditProduct::class.java)
                            productList.add(product!!)
                            addToList(product.name,product.proteins,product.fats,product.carbs,product.calories, product.id)


                        }
                        productAdapter = LimitedSearchedFavouriteProductEditAdapter(applicationContext, meal, id, nameList, proteinsList, fatsList, carbsList, caloriesList)
                        recycler!!.adapter = productAdapter

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun searchForProducts(str: String, recycler: RecyclerView){

        var uid = FirebaseAuth.getInstance().uid ?: ""
        val database = FirebaseDatabase.getInstance()
        val productsQuery = database.reference.child("/userfavourites/$uid").orderByChild("name").startAt(str).endAt(str + "\uf8ff")

        productsQuery.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FavouriteEditProduct::class.java)
                        productList.add(product!!)
                        addToList(product.name,product.proteins,product.fats,product.carbs,product.calories, product.id)

                    }
                    productAdapter = LimitedSearchedFavouriteProductEditAdapter(applicationContext, meal, id, nameList, proteinsList, fatsList, carbsList, caloriesList)
                    recycler.adapter = productAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }



}