package com.example.dietapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.adapters.DietAdapter
import com.example.dietapp.R
import com.example.dietapp.adapters.FavouriteProductsAdapter
import com.example.dietapp.dataclasses.FirebaseFavouriteProduct
import com.example.dietapp.objects.Functions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MyDietsFragment:Fragment(R.layout.fragment_my_diets) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DietAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var caloriesList = mutableListOf<Int>()
    private var idsList = mutableListOf<String>()

    private val uid = FirebaseAuth.getInstance().uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Functions.saveFragment(requireContext(),"cur")

    }



    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val myDiets: RecyclerView = requireView().findViewById<View>(R.id.myDietsRecycler) as RecyclerView
        val addButton: FloatingActionButton = requireView().findViewById<View>(R.id.myDiets_floating_action_button) as FloatingActionButton

        addButton.setOnClickListener {
            val intent = Intent(requireContext(), ChooseEditNameActivity::class.java)
            startActivity(intent)
        }

        myDiets.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = DietAdapter(nameList, caloriesList)
        }

        getMyPlans(uid,myDiets)

    }

    private fun getMyPlans(uid: String, myDiets: RecyclerView) {

        val database = FirebaseDatabase.getInstance()
        val myDietsRef = database.getReference("/userplans/$uid").orderByChild("timestamp")
        val myDietsList = arrayListOf<FirebaseFavouriteProduct>()

        myDietsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearList()
                    for(dietSnapshot in dataSnapshot.children){
                        val diet = dietSnapshot.getValue(FirebaseFavouriteProduct::class.java)
                        myDietsList.add(diet!!)
                        addToList(diet.name,diet.calories,diet.id)
                        myDiets.adapter = DietAdapter(nameList, caloriesList)


                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        myDiets.adapter?.notifyDataSetChanged()

    }

    private fun addToList(name: String, calories: Int, id: String){
        nameList.add(name)
        caloriesList.add(calories)
        idsList.add(id)
    }

    private fun clearList(){
        nameList.clear()
        caloriesList.clear()
        idsList.clear()
    }

}

