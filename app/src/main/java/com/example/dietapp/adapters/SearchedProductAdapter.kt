package com.example.dietapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.main.SubmitProductActivity

class SearchedProductAdapter(context: Context, meal: String, private var names: MutableList<String>, private var proteins: MutableList<Int>, private var fats: MutableList<Int>, private var carbs: MutableList<Int>, private var calories: MutableList<Int>):
    RecyclerView.Adapter<SearchedProductAdapter.ViewHolder>()
{
    val context = context
    val meal = meal

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val searchedProdName: TextView = itemView.findViewById(R.id.searchedProductsName)
        val searchedProdProteins: TextView = itemView.findViewById(R.id.searchedProductsProteins)
        val searchedProdFats: TextView = itemView.findViewById(R.id.searchedProductsFats)
        val searchedProdCarbs: TextView = itemView.findViewById(R.id.searchedProductsCarbs)
        val searchedProdCalories: TextView = itemView.findViewById(R.id.searchedProductsCalories)
        private val searchedProdAdd: ImageView = itemView.findViewById(R.id.searchedProductsAdd)


        init {
            searchedProdAdd.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                addItem(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.searched_products_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchedProdName.text = names[position]
        holder.searchedProdProteins.text = proteins[position].toString()
        holder.searchedProdFats.text = fats[position].toString()
        holder.searchedProdCarbs.text = carbs[position].toString()
        holder.searchedProdCalories.text = calories[position].toString()


    }

    override fun getItemCount(): Int {
        return names.size

    }

    fun addItem(position: Int) {
        val intent = Intent(context, SubmitProductActivity::class.java)
        val name = names[position]
        val proteins = proteins[position]
        val fats = fats[position]
        val carbs = carbs[position]
        val calories = calories[position]
        println(meal)
        println(name)
        println(proteins)
        println(fats)
        println(carbs)
        println(calories)
        intent.putExtra("meal", meal)
        intent.putExtra("name", name)
        intent.putExtra("proteins", proteins)
        intent.putExtra("fats", fats)
        intent.putExtra("carbs", carbs)
        intent.putExtra("calories", calories)
        context.startActivity(intent)
    }

}