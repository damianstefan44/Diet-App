package com.example.dietapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.objects.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FavouriteProductsAdapter(private var names: MutableList<String>, private var proteins: MutableList<Int>, private var fats: MutableList<Int>, private var carbs: MutableList<Int>, private var calories: MutableList<Int>, private var ids: MutableList<String>):
    RecyclerView.Adapter<FavouriteProductsAdapter.ViewHolder>()
{

    private val uid = FirebaseAuth.getInstance().uid ?: ""

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val favProdName: TextView = itemView.findViewById(R.id.favouriteProductsName)
        val favProdProteins: TextView = itemView.findViewById(R.id.favouriteProductsProteins)
        val favProdFats: TextView = itemView.findViewById(R.id.favouriteProductsFats)
        val favProdCarbs: TextView = itemView.findViewById(R.id.favouriteProductsCarbs)
        val favProdCalories: TextView = itemView.findViewById(R.id.favouriteProductsCalories)
        private val favProdDelete: ImageView = itemView.findViewById(R.id.favouriteProductsDelete)


        init {
            favProdDelete.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                removeItem(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favourite_products_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favProdName.text = names[position]
        holder.favProdProteins.text = proteins[position].toString()
        holder.favProdFats.text = fats[position].toString()
        holder.favProdCarbs.text = carbs[position].toString()
        holder.favProdCalories.text = calories[position].toString()


    }

    override fun getItemCount(): Int {
        return names.size

    }

    fun removeItem(position: Int) {

        val database = FirebaseDatabase.getInstance()
        val favRef = database.getReference("/userfavourites/$uid")
        val deleteId = ids[position]

        names.removeAt(position)
        proteins.removeAt(position)
        fats.removeAt(position)
        carbs.removeAt(position)
        calories.removeAt(position)
        ids.removeAt(position)

        favRef.child(deleteId).setValue(null)

        notifyDataSetChanged()
    }

}