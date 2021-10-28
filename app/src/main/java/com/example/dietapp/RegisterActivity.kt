package com.example.dietapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.dietapp.databinding.ActivityLoginBinding
import com.example.dietapp.databinding.ActivityRegisterBinding
import com.example.dietapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.registerUsername
        val password = binding.registerPassword
        val confirmPassword = binding.registerConfirmPassword
        val loading = binding.registerLoading
        val registerButton = binding.registerRegister

        registerButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}