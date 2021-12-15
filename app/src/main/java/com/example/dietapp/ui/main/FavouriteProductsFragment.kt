package com.example.dietapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.adapters.DietAdapter
import com.example.dietapp.adapters.FavouriteProductsAdapter
import com.example.dietapp.R
import com.example.dietapp.adapters.ProductAdapter
import com.example.dietapp.dataclasses.FavouriteProduct
import com.example.dietapp.dataclasses.FirebaseFavouriteProduct
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.objects.Functions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavouriteProductsFragment:Fragment(R.layout.fragment_favourite_products) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FavouriteProductsAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var proteinsList = mutableListOf<Int>()
    private var fatsList = mutableListOf<Int>()
    private var carbsList = mutableListOf<Int>()
    private var caloriesList = mutableListOf<Int>()
    private var idsList = mutableListOf<String>()

    private val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Functions.saveFragment(requireContext(),"cur")

    }



    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val favourites: RecyclerView = requireView().findViewById<View>(R.id.favouriteProductsRecycler) as RecyclerView
        val addButton: FloatingActionButton = requireView().findViewById<View>(R.id.favouriteProducts_floating_action_button) as FloatingActionButton


        addButton.setOnClickListener {
            val intent = Intent(requireContext(), AddFavouriteProductActivity::class.java)
            startActivity(intent)
        }

        favourites.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = FavouriteProductsAdapter(nameList, proteinsList, fatsList, carbsList, caloriesList, idsList)

        }

        getFavouriteProducts(uid,favourites)
    }

    private fun getFavouriteProducts(uid: String, favourites: RecyclerView) {

        val database = FirebaseDatabase.getInstance()
        val favouritesRef = database.getReference("/userfavourites/$uid").orderByChild("timestamp")
        val favouriteProductsList = arrayListOf<FirebaseFavouriteProduct>()

        favouritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearList()
                    for(productSnapshot in dataSnapshot.children){
                        val productName = productSnapshot.key
                        val product = productSnapshot.getValue(FirebaseFavouriteProduct::class.java)
                        favouriteProductsList.add(product!!)
                        addToList(product.name,product.proteins,product.fats,product.carbs,product.calories,product.id)
                        favourites.adapter = FavouriteProductsAdapter(nameList, proteinsList, fatsList, carbsList, caloriesList, idsList)


                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        favourites.adapter?.notifyDataSetChanged()

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

}
