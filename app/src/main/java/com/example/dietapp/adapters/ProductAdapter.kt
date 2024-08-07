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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class ProductAdapter(context: Context, private var meal: String, private var ids: MutableList<String>, private var names: MutableList<String>, private var weights: MutableList<Int>, private var calories: MutableList<Int>, private var eaten: MutableList<Boolean>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>()

{
    val context = context
    val uid = FirebaseAuth.getInstance().uid ?: ""

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productWeight: TextView = itemView.findViewById(R.id.product_weight)
        val productCalories: TextView = itemView.findViewById(R.id.product_calories)
        val productEaten: Button = itemView.findViewById(R.id.product_eaten)
        private val productDelete: ImageView = itemView.findViewById(R.id.product_delete)

        init {
            productDelete.setOnClickListener { v: View ->
                println(ids)
                val position: Int = adapterPosition
                removeItem(position)
            }
            productEaten.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                changeButton(position)
            }

        }



    }

        // Assigning values to layout objects


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_layout,parent,false)
        return ViewHolder(v)

        // Inflating layout
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = names[position]
        holder.productWeight.text = weights[position].toString() + " g"
        holder.productCalories.text = (calories[position]*weights[position]/100).toString() + " kcal"
        when (eaten[position]) {
            true -> {
                holder.productEaten.text = "Zjedzono"
                var buttonDrawable: Drawable? = holder.productEaten.background
                buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
                DrawableCompat.setTint(buttonDrawable, Color.rgb(180, 255, 180))
                holder.productEaten.background = buttonDrawable

            }
            false -> {holder.productEaten.text = "Zjedz"
                var buttonDrawable: Drawable? = holder.productEaten.background
                buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
                DrawableCompat.setTint(buttonDrawable, Color.rgb(255, 128, 128))
                holder.productEaten.background = buttonDrawable
            }
        }

        // This is what is going to be seen in each recyclerview item
    }

    override fun getItemCount(): Int {
        return names.size

        // Size of lists passed to adapter
    }

    fun removeItem(position: Int) {

        val date = Functions.getDate(context).toString()
        val database = FirebaseDatabase.getInstance()
        val mealRef = database.getReference("/userdata/$uid/$date/$meal")

        val deleteId = ids[position]

        ids.removeAt(position)
        names.removeAt(position)
        weights.removeAt(position)
        calories.removeAt(position)
        eaten.removeAt(position)

        mealRef.child(deleteId).setValue(null)

        notifyDataSetChanged()
    }

    fun changeButton(position: Int) {

        val date = Functions.getDate(context).toString()
        val database = FirebaseDatabase.getInstance()
        val id = ids[position]
        val mealRef = database.getReference("/userdata/$uid/$date/$meal/$id")

        if(eaten[position]) {

            mealRef.child("eaten").setValue(false)
        }
        else{
            mealRef.child("eaten").setValue(true)
        }
        notifyDataSetChanged()
    }



}