package com.example.utstiara.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.utstiara.databinding.ActivityProfileBinding
import com.example.utstiara.datastore.DataStoreManager
import com.example.utstiara.login.Login
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        observeLoginState()
        setupButtonClick()
        insertData()
    }

    private fun observeLoginState() {
        dataStoreManager.readLoginFlow.asLiveData().observe(this) { isLoggedIn ->
            if (!isLoggedIn) {
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
    }

    private fun insertData() {
        lifecycleScope.launch {
            dataStoreManager.getUsername().collect {
                val firstLetter: String = it.substring(0, 1).uppercase()
                binding.title.text = firstLetter
                binding.username.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getGitHub().collect {
                binding.github.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getNim().collect {
                binding.nim.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getEmail().collect {
                binding.email.text = it
            }
        }
    }

    private fun setupButtonClick() {
        binding.bthome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btlogout.setOnClickListener {
            lifecycleScope.launch {
                dataStoreManager.updateLogin(false)
            }
        }
    }
}
