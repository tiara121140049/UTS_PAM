package com.example.utstiara

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.utstiara.databinding.ActivitySplashScreenBinding
import com.example.utstiara.datastore.DataStoreManager
import com.example.utstiara.homepage.MainActivity
import com.example.utstiara.login.Login


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStoreManager(applicationContext)

        val intent = if (dataStoreManager.readLoginFlow.asLiveData().value == true) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, Login::class.java)
        }

        binding.ivSplash.alpha = 0f
        binding.textView.alpha = 0f
        binding.textView.animate().setDuration(3000).alpha(1f)
        binding.ivSplash.animate().setDuration(3000).alpha(1f).withEndAction {
            startActivity(intent)
            finish()
        }
    }
}
