package com.example.dietapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.ui.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var mAuth: FirebaseAuth? = null
    private val username = null
    private val password = null
    private val confirmPassword = null
    private val nickname = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.registerUsername
        val password = binding.registerPassword
        val confirmPassword = binding.registerConfirmPassword
        val nickname = binding.registerNickname
        val loading = binding.registerLoading
        val registerButton = binding.registerRegister
        mAuth = FirebaseAuth.getInstance()


        registerButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser(){

        val mail = binding.registerUsername
        val pass = binding.registerPassword
        val confPass = binding.registerConfirmPassword
        val nick = binding.registerNickname
        val load = binding.registerLoading

        val email = mail.text.toString().trim()
        val password = pass.text.toString().trim()
        val confirmPassword = confPass.text.toString().trim()
        val nickname = nick.text.toString().trim()

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
        if(nickname.isNullOrEmpty()){
            nick.error = "Pseudonim jest wymagany!"
            nick.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.error = "Niepoprawny email!"
            mail.requestFocus()
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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                // save to firebase
                saveUserToFirebaseDatabase(it.toString())
                Log.d(
                    "TAG",
                    "Successfully registered user in firebase (uid: ${it.result?.user?.uid})"
                )


            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to register user: ${it.message}")
                Toast.makeText(this, "Failed to register user: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }




    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val mail = binding.registerUsername
        val nick = binding.registerNickname
        val load = binding.registerLoading

        val user = FirebaseUser(uid, mail.text.toString(),nick.text.toString())

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("TAG", "Finally we saved the user to Firebase Database")
                load.visibility = View.GONE
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Log.d("TAG", "Failed to set value to database: ${it.message}")
                load.visibility = View.GONE
            }
    }

}

class FirebaseUser(val uid: String,val email: String, val nickname: String)