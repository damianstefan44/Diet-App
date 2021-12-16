package com.example.dietapp.ui.main

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.graphics.drawable.DrawableCompat
import com.example.dietapp.R
import com.example.dietapp.adapters.ProductEditAdapter
import com.example.dietapp.dataclasses.FirebaseAgreeToSearch
import com.example.dietapp.dataclasses.FirebaseMealProduct
import com.example.dietapp.dataclasses.FirebaseSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsActivity : AppCompatActivity() {

    val uid = FirebaseAuth.getInstance().uid ?: ""
    var agree: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val agreeButton: Button = findViewById(R.id.settings_change_button)

        getSetting(agreeButton)

        agreeButton.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val settingsRef = database.getReference("/settings/$uid")

            settingsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        val setting = dataSnapshot.getValue(FirebaseSettings::class.java)
                        if(setting!!.agreeToSearch){
                            settingsRef.setValue(FirebaseSettings(setting.id,setting.username,false,setting.admin))
                                .addOnSuccessListener {
                                    Log.d("TAG", "Successfully changed setting")
                                }
                                .addOnFailureListener {
                                    Log.d("TAG", "Failed to change setting: ${it.message}")
                                }
                        }
                        else{
                            settingsRef.setValue(FirebaseSettings(setting.id,setting.username,true,setting.admin))
                                .addOnSuccessListener {
                                    Log.d("TAG", "Successfully changed setting")
                                }
                                .addOnFailureListener {
                                    Log.d("TAG", "Failed to change setting: ${it.message}")
                                }
                        }
                    }
                    else{
                        Log.d("Ustawienia","Nie pobrano")
                        //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })

        }

    }

    fun swipeButton(button: Button, agree: Boolean){

        if(agree){
            button.text = "TAK"
            var buttonDrawable: Drawable? = button.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
            DrawableCompat.setTint(buttonDrawable, Color.rgb(180, 255, 180))
            button.background = buttonDrawable
        }
        else{
            button.text = "NIE"
            var buttonDrawable: Drawable? = button.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
            DrawableCompat.setTint(buttonDrawable, Color.rgb(255, 128, 128))
            button.background = buttonDrawable
        }


    }

    private fun getSetting(button: Button){

        val database = FirebaseDatabase.getInstance()
        val settingsRef = database.getReference("/settings/$uid")

        settingsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val setting = dataSnapshot.getValue(FirebaseSettings::class.java)
                    if(setting!!.agreeToSearch){
                        agree = true
                        swipeButton(button, agree)
                    }
                    else{
                        agree = false
                        swipeButton(button, agree)
                    }
                }
                else{
                    Log.d("Ustawienia","Nie pobrano")
                    //getCalories(breakfastWeightList, breakfastCaloriesList, secondBreakfastWeightList, secondBreakfastCaloriesList, lunchWeightList, lunchCaloriesList, snackWeightList, snackCaloriesList, dinnerWeightList, dinnerCaloriesList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })


    }
}