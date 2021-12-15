package com.example.dietapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.main.UserDietsActivity

class LimitedSearchedUserAdapter(context: Context, private var names: MutableList<String>, private var ids: MutableList<String>):
    RecyclerView.Adapter<LimitedSearchedUserAdapter.ViewHolder>()
{
    val context = context
    private val limit = 15

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val searchedUsersName: TextView = itemView.findViewById(R.id.searchedUsersName)


        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                loadUserDiets(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.searched_users_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchedUsersName.text = names[position]
    }

    override fun getItemCount(): Int {
        return if(names.size > limit){
            limit
        } else {
            names.size
        }

    }

    fun loadUserDiets(position: Int) {
        val intent = Intent(context, UserDietsActivity::class.java)
        val name = names[position]
        val id = ids[position]

        intent.putExtra("name", name)
        intent.putExtra("id", id)
        context.startActivity(intent)
    }

}