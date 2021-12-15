package com.example.dietapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.dietapp.R

class ChooseEditNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_edit_name)

        val name: EditText = findViewById(R.id.choose_edit_name_name)
        val add: Button = findViewById(R.id.choose_edit_name_add)


        add.setOnClickListener {
            if(!name.text.isNullOrEmpty()){
                val intent = Intent(applicationContext, EditDietActivity::class.java)
                intent.putExtra("planName", name.text.toString())
                intent.putExtra("id","false")
                startActivity(intent)
                finish()

            }
            else{
                name.error = "Nazwa jest wymagana!"
                name.requestFocus()
            }
        }
    }
}