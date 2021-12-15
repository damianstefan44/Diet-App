package com.example.dietapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.dataclasses.FirebaseSettings
import com.example.dietapp.dataclasses.FirebaseUser
import com.example.dietapp.dataclasses.FirebaseUsername
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.login.ForgotPasswordActivity
import com.example.dietapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Functions.saveFragment(applicationContext,"cur")

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.registerEmail
        val password = binding.registerPassword
        val confirmPassword = binding.registerConfirmPassword
        val username = binding.registerUsername
        val loading = binding.registerLoading
        val registerButton = binding.registerRegister
        val haveAccount = binding.registerHaveAccount


        registerButton.setOnClickListener {
            registerUser()
        }

        haveAccount.setOnClickListener{
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser(){

        val mail = binding.registerEmail
        val pass = binding.registerPassword
        val confPass = binding.registerConfirmPassword
        val usr = binding.registerUsername
        val load = binding.registerLoading
        var allow: Boolean = true

        val email = mail.text.toString().trim()
        val password = pass.text.toString().trim()
        val confirmPassword = confPass.text.toString().trim()
        val username = usr.text.toString().trim()

        if(email.isNullOrEmpty()){
            mail.error = "Email jest wymagany!"
            mail.requestFocus()
            return
        }
        if(password.isNullOrEmpty()){
            pass.error = "Hasło jest wymagane!"
            pass.requestFocus()
            return
        }
        if(confirmPassword.isNullOrEmpty()){
            confPass.error = "Hasło jest wymagane!"
            confPass.requestFocus()
            return
        }
        if(username.isNullOrEmpty()){
            usr.error = "Pseudonim jest wymagany!"
            usr.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.error = "Niepoprawny email!"
            mail.requestFocus()
            return
        }
        if(username.length < 6 ){
            usr.error = "Pseudonim jest za krótki!"
            usr.requestFocus()
            return
        }
        if(!(password == confirmPassword && password == pass.text.toString())){
            confPass.error = "Hasła się nie zgadzają!"
            confPass.requestFocus()
            return
        }
        if(password.length < 6 || confirmPassword.length < 6){
            confPass.error = "Hasło jest za krótkie!"
            confPass.requestFocus()
            return
        }

        load.visibility = View.VISIBLE


        val rootRef1 = FirebaseDatabase.getInstance().reference.child("usernames")


        val usernameDuplicateQuery: Query = FirebaseDatabase.getInstance().reference.child("users").orderByChild("username").equalTo(username)

        usernameDuplicateQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (!it.isSuccessful) {
                                return@addOnCompleteListener
                            }
                            // save to firebase
                            saveUserToFirebaseDatabase()
                            Log.d(
                                "TAG",
                                "Successfully registered user in firebase (uid: ${it.result?.user?.uid})"
                            )
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()


                        }
                        .addOnFailureListener {
                            Log.d("TAG", "Failed to register user: ${it.message}")
                            Toast.makeText(
                                applicationContext,
                                "Failed to register user: ${it.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            load.visibility = View.GONE
                        }
                }
                else{
                    usr.error = "Pseudonim jest zajęty!"
                    usr.requestFocus()
                    load.visibility = View.GONE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message)
            }
        })


    }

    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val mail = binding.registerEmail
        val usr = binding.registerUsername
        val load = binding.registerLoading
        val dbUsr = usr.text.toString().trim().lowercase()
        val ref2 = FirebaseDatabase.getInstance().getReference("/usernames/$uid")
        val ref3 = FirebaseDatabase.getInstance().getReference("/settings/$uid")

        val user = FirebaseUser(mail.text.toString(),usr.text.toString())
        val username = FirebaseUsername(dbUsr)
        val settings = FirebaseSettings(uid, dbUsr, agreeToSearch = true, admin = false)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("TAG", "Finally we saved the user to Firebase Database")
                load.visibility = View.GONE
            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to set value to database: ${it.message}")
                load.visibility = View.GONE
            }
        ref2.setValue(username)
            .addOnSuccessListener {
                Log.d("TAG", "Finally we saved the username to Firebase Database")

            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to set username to database: ${it.message}")

            }
        ref3.setValue(settings)
            .addOnSuccessListener {
                Log.d("TAG", "Successfully saved user settings to Firebase Database")

            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to save user settings to database: ${it.message}")

            }
    }

}
