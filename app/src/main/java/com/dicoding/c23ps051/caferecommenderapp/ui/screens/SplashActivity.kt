package com.dicoding.c23ps051.caferecommenderapp.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.c23ps051.caferecommenderapp.MainActivity
import com.dicoding.c23ps051.caferecommenderapp.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        runnable = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        handler.postDelayed(runnable, 2000)
    }

    override fun onBackPressed() {
        handler.removeCallbacks(runnable)
        finish()
    }
}