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
import com.example.dietapp.ui.main.EditDietActivity
import com.example.dietapp.ui.main.LoadDietActivity
import com.example.dietapp.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DietAdapter(context: Context, private var names: MutableList<String>, private var calories: MutableList<Int>, private var ids: MutableList<String>,):
    RecyclerView.Adapter<DietAdapter.ViewHolder>()
{

    val uid = FirebaseAuth.getInstance().uid ?: ""
    val context = context

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dietName: TextView = itemView.findViewById(R.id.dietName)
        val dietCalories: TextView = itemView.findViewById(R.id.dietCalories)
        private val dietDelete: ImageView = itemView.findViewById(R.id.dietDelete)
        private val dietEdit: ImageView = itemView.findViewById(R.id.dietEdit)
        private val dietLoad: ImageView = itemView.findViewById(R.id.dietLoad)


        init {
            dietDelete.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                removeItem(position)
            }
            dietEdit.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                editItem(position)
            }
            dietLoad.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                loadItem(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diet_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dietName.text = names[position]
        holder.dietCalories.text = calories[position].toString()


    }

    override fun getItemCount(): Int {
        return names.size

    }

    fun removeItem(position: Int) {

        val database = FirebaseDatabase.getInstance()
        val dietRef = database.getReference("/userplans/$uid")
        val deleteId = ids[position]
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        println(deleteId)
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")

        names.removeAt(position)
        calories.removeAt(position)
        ids.removeAt(position)

        dietRef.child(deleteId).setValue(null)

        notifyDataSetChanged()
    }

    fun editItem(position: Int) {

        val intent = Intent(context, EditDietActivity::class.java)
        intent.putExtra("planName", names[position])
        intent.putExtra("id",ids[position])
        context.startActivity(intent)
    }

    fun loadItem(position: Int) {

        val intent = Intent(context, LoadDietActivity::class.java)
        intent.putExtra("name", names[position])
        intent.putExtra("calories", calories[position])
        intent.putExtra("id", ids[position])
        context.startActivity(intent)
    }

}