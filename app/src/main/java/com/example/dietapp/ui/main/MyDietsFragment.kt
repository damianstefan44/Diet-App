package com.example.dietapp.ui.main

import android.R.layout
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.DietAdapter
import com.example.dietapp.dataclasses.MyDietsProduct
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

        val ctx = requireActivity().applicationContext

        val myDiets: RecyclerView =
            requireView().findViewById<View>(R.id.myDietsRecycler) as RecyclerView
        val addButton: FloatingActionButton =
            requireView().findViewById<View>(R.id.myDiets_floating_action_button) as FloatingActionButton


        addButton.setOnClickListener {
            val intent = Intent(requireContext(), ChooseEditNameActivity::class.java)
            startActivity(intent)
        }

        myDiets.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = DietAdapter(ctx, nameList, caloriesList, idsList)
        }

        getMyPlans(ctx, uid, myDiets)

    }

    private fun getMyPlans(ctx: Context, uid: String, myDiets: RecyclerView) {

        val database = FirebaseDatabase.getInstance()
        val myDietsRef = database.getReference("/userplans/$uid").orderByChild("values/timestamp")
        val myDietsList = arrayListOf<MyDietsProduct>()

        myDietsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    clearList()
                    for(dietSnapshot in dataSnapshot.children){
                        val diet = dietSnapshot.child("/values").getValue(MyDietsProduct::class.java)
                        if(diet != null) {
                            myDietsList.add(diet!!)
                            addToList(diet.name, diet.calories, diet.id)
                            myDiets.adapter = DietAdapter(ctx, nameList, caloriesList, idsList)
                        }


                    }
                }
                else{
                    clearList()
                    myDiets.adapter = DietAdapter(ctx, nameList, caloriesList, idsList)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG","The read failed: " + databaseError.code)
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

