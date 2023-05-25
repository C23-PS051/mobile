package com.dicoding.c23ps051.caferecommenderapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.CafeRecommenderApp
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.home.HomeScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.recommended.RecommendedScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivity(Intent(this, HomeScreen::class.java))
//        finish()
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    CafeRecommenderApp()
                }
            }
        }
    }
}