package com.example.dietapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.dietapp.ui.main.MainActivity
import com.example.dietapp.databinding.ActivityLoginBinding

import com.example.dietapp.R
import com.example.dietapp.objects.Functions
import com.example.dietapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Functions.saveFragment(applicationContext,"cur")

        val username = binding.loginUsername
        val password = binding.loginPassword
        val login = binding.loginLogin
        val loading = binding.loginLoading
        val forgot = binding.loginForgotPassword
        val register = binding.loginRegister

        login.setOnClickListener {
            loginUser()
        }
        forgot.setOnClickListener {
            val intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        register.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart(){
        super.onStart()
        isCurrentUser()

    }

    private fun loginUser() {

        val email = findViewById<EditText>(R.id.login_username).text?.trim().toString()
        val password = findViewById<EditText>(R.id.login_password).text?.trim().toString()
        val loading = binding.loginLoading


        loading.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                if(authResult.user != null) {
                    val intent = Intent(applicationContext, MainActivity::class.java).apply {
                        flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    loading.visibility = View.GONE
                    startActivity(intent)
                }
                loading.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                val pass = findViewById<EditText>(R.id.login_password)
                loading.visibility = View.GONE
                pass.error = "Zły email lub hasło!"
                pass.requestFocus()
                Log.d(LOG_DEBUG, exception.message.toString())
            }
    }

    private fun isCurrentUser(){

        mAuth.currentUser?.let {auth ->
            val intent = Intent(applicationContext, MainActivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }

    }




}