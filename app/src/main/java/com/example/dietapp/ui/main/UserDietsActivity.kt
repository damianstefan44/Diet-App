package com.example.dietapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.DietAdapter
import com.example.dietapp.adapters.UserDietAdapter
import com.example.dietapp.dataclasses.MyDietsProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDietsActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DietAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var caloriesList = mutableListOf<Int>()
    private var idsList = mutableListOf<String>()
    var id: String = ""
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_diets)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            id = bundle.getString("id").toString()
            name = bundle.getString("name").toString()
        }

        val userDiets: RecyclerView = findViewById<View>(R.id.userDietsRecycler) as RecyclerView

        userDiets.layoutManager = LinearLayoutManager(applicationContext)
        userDiets.adapter = UserDietAdapter(applicationContext, id, nameList, caloriesList, idsList)

        getMyPlans(applicationContext, id, userDiets)


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
                            myDiets.adapter = UserDietAdapter(ctx, uid, nameList, caloriesList, idsList)
                        }


                    }
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