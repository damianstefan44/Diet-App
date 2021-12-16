package com.example.dietapp.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val send = binding.forgotSend

        send.setOnClickListener {
            sendCode()
        }

    }

    private fun sendCode() {
        val email = binding.forgotEmail
        val emailText = binding.forgotEmail.text.trim().toString()
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ForgotPasswordActivity", "Email sent.")
                    Toast.makeText(applicationContext,"Wysłano email", Toast.LENGTH_LONG).show()
                }
                else{
                    email.error = "Podaj poprawny email"
                    email.requestFocus()
                }
            }
    }


}