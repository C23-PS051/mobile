package com.dicoding.c23ps051.caferecommenderapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.c23ps051.caferecommenderapp.ui.App
import com.dicoding.c23ps051.caferecommenderapp.ui.HomeActivity
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            CafeRecommenderAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    App()
//                }
//            }
//        }

        startActivity(Intent(this, HomeActivity::class.java))
    }
}