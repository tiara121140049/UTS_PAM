package com.example.utstiara.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.utstiara.databinding.ActivitySignUpBinding
import com.example.utstiara.datastore.DataStoreManager
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        binding.signUpBt.setOnClickListener {
            val user = binding.inputUsername.text.toString()
            val pw = binding.inputPassword.text.toString()
            val git = binding.inputGithub.text.toString()
            val nim = binding.inputNim.text.toString()
            val email = binding.inputEmail.text.toString()

            if (user.isNotEmpty() && pw.isNotEmpty() && git.isNotEmpty() && nim.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    dataStoreManager.saveToDataStore(user, pw, git, nim, email)
                }
                Toast.makeText(this@Register, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Register, Login::class.java))
            } else {
                Toast.makeText(this@Register, "Please fill in all the fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
