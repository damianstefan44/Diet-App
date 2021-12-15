package com.example.dietapp.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.dataclasses.Product
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.main.CurrentDayFragment
import com.example.dietapp.ui.main.EditDietActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class ProductShowAdapter(private var ids: MutableList<String>, private var names: MutableList<String>, private var weights: MutableList<Int>, private var calories: MutableList<Int>, private var eaten: MutableList<Boolean>):
    RecyclerView.Adapter<ProductShowAdapter.ViewHolder>()

{


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val productName: TextView = itemView.findViewById(R.id.product_show_name)
        val productWeight: TextView = itemView.findViewById(R.id.product_show_weight)
        val productCalories: TextView = itemView.findViewById(R.id.product_show_calories)

    }

        // Assigning values to layout objects


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_show_layout,parent,false)
        return ViewHolder(v)

        // Inflating layout
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = names[position]
        holder.productWeight.text = weights[position].toString() + " g"
        holder.productCalories.text = (calories[position]*weights[position]/100).toString() + " kcal"

        // This is what is going to be seen in each recyclerview item
    }

    override fun getItemCount(): Int {
        return names.size

        // Size of lists passed to adapter
    }

}