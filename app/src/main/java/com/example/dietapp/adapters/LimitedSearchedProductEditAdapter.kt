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
import com.example.dietapp.ui.main.SubmitProductEditActivity

class LimitedSearchedProductEditAdapter(context: Context, meal: String, id: String, planName: String, private var names: MutableList<String>, private var proteins: MutableList<Int>, private var fats: MutableList<Int>, private var carbs: MutableList<Int>, private var calories: MutableList<Int>):
    RecyclerView.Adapter<LimitedSearchedProductEditAdapter.ViewHolder>()
{
    val context = context
    val meal = meal
    val id = id
    val planName = planName
    private val limit = 15

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
        holder.searchedProdProteins.text = proteins[position].toString() + " g"
        holder.searchedProdFats.text = fats[position].toString() + " g"
        holder.searchedProdCarbs.text = carbs[position].toString() + " g"
        holder.searchedProdCalories.text = calories[position].toString() + " kcal"


    }

    override fun getItemCount(): Int {
        return if(names.size > limit){
            limit
        } else {
            names.size
        }

    }

    fun addItem(position: Int) {
        val intent = Intent(context, SubmitProductEditActivity::class.java)
        val name = names[position]
        val proteins = proteins[position]
        val fats = fats[position]
        val carbs = carbs[position]
        val calories = calories[position]
        intent.putExtra("meal", meal)
        intent.putExtra("id", id)
        intent.putExtra("planName", planName)
        intent.putExtra("name", name)
        intent.putExtra("proteins", proteins)
        intent.putExtra("fats", fats)
        intent.putExtra("carbs", carbs)
        intent.putExtra("calories", calories)
        context.startActivity(intent)
    }

}