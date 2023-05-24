package com.dicoding.c23ps051.caferecommenderapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.dicoding.c23ps051.caferecommenderapp.ui.LoginActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}