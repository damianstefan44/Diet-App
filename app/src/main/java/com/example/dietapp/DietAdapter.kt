package com.example.dietapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class DietAdapter(private var names: List<String>, private var calories: List<String>):
    RecyclerView.Adapter<DietAdapter.ViewHolder>()
{

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dietName: TextView = itemView.findViewById(R.id.dietName)
        val dietCalories: TextView = itemView.findViewById(R.id.dietCalories)


        init {
            itemView.setOnClickListener{ v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "you clicked on item: ${position + 1}",Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diet_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dietName.text = names[position]
        holder.dietCalories.text = calories[position]


    }

    override fun getItemCount(): Int {
        return names.size

    }

}