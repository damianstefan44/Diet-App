package com.example.dietapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.dataclasses.*
import com.example.dietapp.ui.main.EditDietActivity
import com.example.dietapp.ui.main.LoadDietActivity
import com.example.dietapp.ui.main.MainActivity
import com.example.dietapp.ui.main.ShowDietActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserDietAdapter(context: Context, uid: String, private var names: MutableList<String>, private var calories: MutableList<Int>, private var ids: MutableList<String>):
    RecyclerView.Adapter<UserDietAdapter.ViewHolder>()
{

    val uid = uid
    val context = context
    val myUid = FirebaseAuth.getInstance().uid ?: ""
    var cal: Int = 0
    var name: String = ""

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dietName: TextView = itemView.findViewById(R.id.userDietName)
        val dietCalories: TextView = itemView.findViewById(R.id.userDietCalories)
        private val dietLoad: ImageView = itemView.findViewById(R.id.userDietLoad)
        private val dietEdit: ImageView = itemView.findViewById(R.id.userDietEdit)


        init {
            dietLoad.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                loadItem(position)
            }
            dietEdit.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                showItem(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_diet_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dietName.text = names[position]
        holder.dietCalories.text = calories[position].toString()


    }

    override fun getItemCount(): Int {
        return names.size

    }

    fun loadItem(position: Int) {

        val id = ids[position]

        val database = FirebaseDatabase.getInstance()

        val dietRef = database.getReference("/userplans/$myUid")
        val newId = dietRef.push().key.toString()

        val dietRef2 = database.getReference("/userplans/$uid/$id")


        dietRef2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        if(productSnapshot.key == "values"){
                            val product = productSnapshot.getValue(FirebaseDietValues::class.java)
                            cal = product!!.calories
                            name = product.name
                            copyData(cal,name,newId,id)

                        }

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG","The read failed: " + databaseError.code)
            }
        })





    }

    fun copyData(cal: Int, name: String, newId: String, id: String){

        val database = FirebaseDatabase.getInstance()
        val breakfastRef = database.getReference("/userplans/$uid/$id/breakfast").orderByChild("timestamp")
        val secondBreakfastRef = database.getReference("/userplans/$uid/$id/secondbreakfast").orderByChild("timestamp")
        val lunchRef = database.getReference("/userplans/$uid/$id/lunch").orderByChild("timestamp")
        val snackRef = database.getReference("/userplans/$uid/$id/snack").orderByChild("timestamp")
        val dinnerRef = database.getReference("/userplans/$uid/$id/dinner").orderByChild("timestamp")
        val breakfastProductsList = arrayListOf<FirebaseMealProduct>()
        val secondBreakfastProductsList = arrayListOf<FirebaseMealProduct>()
        val lunchProductsList = arrayListOf<FirebaseMealProduct>()
        val snackProductsList = arrayListOf<FirebaseMealProduct>()
        val dinnerProductsList = arrayListOf<FirebaseMealProduct>()


        val brRef = database.getReference("/userplans/$myUid/$newId/breakfast")
        val secRef = database.getReference("/userplans/$myUid/$newId/secondbreakfast")
        val lunRef = database.getReference("/userplans/$myUid/$newId/lunch")
        val snRef = database.getReference("/userplans/$myUid/$newId/snack")
        val dinRef = database.getReference("/userplans/$myUid/$newId/dinner")


        val dietRef3 = database.getReference("/userplans/$myUid/$newId")

        val dietValues = DietValues(newId, name, cal,
            ServerValue.TIMESTAMP)

        dietRef3.child("values").setValue(dietValues)

        breakfastRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        breakfastProductsList.add(product!!)
                        val productId = brRef.push().key
                        val loadProduct = CopyDietsProduct(productId.toString(),product.name, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        brRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        secondBreakfastRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        secondBreakfastProductsList.add(product!!)
                        val productId = secRef.push().key
                        val loadProduct = CopyDietsProduct(productId.toString(),product.name, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        secRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        lunchRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        lunchProductsList.add(product!!)
                        val productId = lunRef.push().key
                        val loadProduct = CopyDietsProduct(productId.toString(),product.name, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        lunRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        snackRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        snackProductsList.add(product!!)
                        val productId = snRef.push().key
                        val loadProduct = CopyDietsProduct(productId.toString(),product.name, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        snRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        dinnerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(productSnapshot in dataSnapshot.children){
                        val product = productSnapshot.getValue(FirebaseMealProduct::class.java)
                        dinnerProductsList.add(product!!)
                        val productId = dinRef.push().key
                        val loadProduct = CopyDietsProduct(productId.toString(),product.name, product.calories, product.carbs, product.fats, product.proteins, product.weight,
                            ServerValue.TIMESTAMP)
                        dinRef.child(productId!!).setValue(loadProduct)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        Toast.makeText(context, "Dodano $name do planów", Toast.LENGTH_SHORT).show()

    }

    fun showItem(position: Int) {

        val dietId = ids[position]
        val intent = Intent(context, ShowDietActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("id", dietId)
        context.startActivity(intent)
    }


}