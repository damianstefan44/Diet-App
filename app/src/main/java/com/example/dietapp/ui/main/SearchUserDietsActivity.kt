package com.example.dietapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.adapters.LimitedSearchedProductAdapter
import com.example.dietapp.adapters.LimitedSearchedUserAdapter
import com.example.dietapp.adapters.SearchedProductAdapter
import com.example.dietapp.dataclasses.FirebaseProduct
import com.example.dietapp.dataclasses.FirebaseSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchUserDietsActivity : AppCompatActivity() {

    var userAdapter: LimitedSearchedUserAdapter? = null
    var recycler: RecyclerView? = null
    var searchEditText: EditText? = null
    private var usernameList = mutableListOf<String>()
    private var idsList = mutableListOf<String>()

    private var userList = mutableListOf<FirebaseSettings>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user_diets)

        val recycler = findViewById<RecyclerView>(R.id.search_user_diets_recycler)
        val searchEditText = findViewById<EditText>(R.id.search_user_diets_search)


        retrieveUsers(searchEditText)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        val search = findViewById<ImageView>(R.id.search_user_diets_search_button)


        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearList()
                userAdapter = LimitedSearchedUserAdapter(applicationContext, usernameList, idsList)
                recycler!!.adapter = userAdapter
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        search.setOnClickListener {
            val s = searchEditText.text.toString()
            searchForUsers(s,recycler)
        }

    }

    private fun addToList(username: String, id: String){
        usernameList.add(username)
        idsList.add(id)
    }

    private fun clearList(){
        usernameList.clear()
        idsList.clear()
    }

    private fun retrieveUsers(searchText: EditText) {

        val database = FirebaseDatabase.getInstance()
        val userRef = database.reference.child("settings").orderByChild("agreeToSearch").equalTo(true)

        userRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    if(searchText.text.equals("")){
                        for(userSnapshot in dataSnapshot.children){
                            val user = userSnapshot.getValue(FirebaseSettings::class.java)
                            if(user!!.agreeToSearch) {
                                userList.add(user)
                                addToList(user.username, user.id)
                            }


                        }
                        userAdapter = LimitedSearchedUserAdapter(applicationContext, usernameList, idsList)
                        recycler!!.adapter = userAdapter

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG","The read failed: " + databaseError.code)
            }

        })

    }

    private fun searchForUsers(str: String, recycler: RecyclerView){

        val database = FirebaseDatabase.getInstance()
        val userQuery = database.reference.child("settings").orderByChild("username").startAt(str).endAt(str + "\uf8ff")

        userQuery.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearList()
                if(dataSnapshot.exists()){
                    for(userSnapshot in dataSnapshot.children){
                        val user = userSnapshot.getValue(FirebaseSettings::class.java)
                        if(user!!.agreeToSearch) {
                            userList.add(user!!)
                            addToList(user.username, user.id)
                        }

                    }
                    userAdapter = LimitedSearchedUserAdapter(applicationContext, usernameList, idsList)
                    recycler!!.adapter = userAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG","The read failed: " + databaseError.code)
            }

        })

    }



}