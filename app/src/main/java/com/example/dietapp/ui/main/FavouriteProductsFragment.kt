package com.example.dietapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.adapters.DietAdapter
import com.example.dietapp.adapters.FavouriteProductsAdapter
import com.example.dietapp.R


class FavouriteProductsFragment:Fragment(R.layout.fragment_favourite_products) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DietAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var proteinsList = mutableListOf<Int>()
    private var fatsList = mutableListOf<Int>()
    private var carbsList = mutableListOf<Int>()
    private var caloriesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(nameList.isEmpty()){
            postToList()
        }
    }



    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView: RecyclerView = requireView().findViewById<View>(R.id.favouriteProductsRecycler) as RecyclerView



        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = FavouriteProductsAdapter(nameList, proteinsList, fatsList, carbsList, caloriesList)
        }
    }

    private fun addToList(name: String, proteins: Int, fats: Int, carbs: Int, calories: Int){
        nameList.add(name)
        proteinsList.add(proteins)
        fatsList.add(fats)
        carbsList.add(carbs)
        caloriesList.add(calories)
    }

    private fun postToList(){
        for(i in 1..10){
            addToList("name $i", 50, 79, 129, 2399)
        }

    }
}
