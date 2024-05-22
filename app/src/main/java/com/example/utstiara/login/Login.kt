package com.example.utstiara.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.utstiara.databinding.ActivitySignInBinding
import com.example.utstiara.datastore.DataStoreManager
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.utstiara.homepage.MainActivity
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        dataStoreManager.readLoginFlow.asLiveData().observe(this) { loggedIn ->
            if (loggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.signInBt.setOnClickListener {
            val user = binding.inputUsername.text.toString()
            val pw = binding.inputPassword.text.toString()

            if (user.isNotEmpty() && pw.isNotEmpty()) {
                observeInput(user, pw)
            } else {
                Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpOp.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun observeInput(user: String, password: String) {
        var userCorrect = false
        var passwordCorrect = false

        dataStoreManager.readUserFlow.asLiveData().observe(this) { storedUser ->
            if (storedUser == user) {
                userCorrect = true
            }
        }

        dataStoreManager.readPwFlow.asLiveData().observe(this) { storedPassword ->
            if (storedPassword == password) {
                passwordCorrect = true
            }
        }

        if (userCorrect && passwordCorrect) {
            lifecycleScope.launch {
                dataStoreManager.updateLogin(true)
            }
        } else {
            Toast.makeText(this, "Incorrect username or password!", Toast.LENGTH_SHORT).show()
        }
    }
}
