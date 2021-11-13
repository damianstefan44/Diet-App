package com.example.dietapp

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

class DietProductAdapter(private var names: MutableList<String>,private var weights: MutableList<String>, private var calories: MutableList<Int>,private var eaten: MutableList<Boolean>):
    RecyclerView.Adapter<DietProductAdapter.ViewHolder>()
{

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val productName: TextView = itemView.findViewById(R.id.diet_product_name)
        val productWeight: TextView = itemView.findViewById(R.id.diet_product_weight)
        val productCalories: TextView = itemView.findViewById(R.id.diet_product_calories)
        private val productDelete: ImageView = itemView.findViewById(R.id.diet_product_delete)

        init {
            productDelete.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                removeItem(position)
            }
        }

    }

    // Assigning values to layout objects

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diet_product_layout,parent,false)
        return ViewHolder(v)

        // Inflating layout
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = names[position]
        holder.productWeight.text = weights[position]
        holder.productCalories.text = calories[position].toString() + " kcal"

        // This is what is going to be seen in each recyclerview item
    }

    override fun getItemCount(): Int {
        return names.size

        // Size of lists passed to adapter
    }

    fun removeItem(position: Int) {
        names.removeAt(position)
        weights.removeAt(position)
        calories.removeAt(position)

        notifyDataSetChanged()
    }


}