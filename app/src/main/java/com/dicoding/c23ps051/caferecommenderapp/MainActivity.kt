package com.dicoding.c23ps051.caferecommenderapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.CafeRecommenderApp
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.sign_in.SignInScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: PreferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivity(Intent(this, HomeScreen::class.java))
//        finish()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[PreferenceViewModel::class.java]

        viewModel.getLogin().observe(this) { user ->
            if (user.isLogin) {
                setContent {
                    CafeRecommenderAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background,
                        ) {
                            CafeRecommenderApp(userPreference = UserPreference.getInstance(dataStore))
                        }
                    }
                }
            } else {
                setContent {
                    CafeRecommenderAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background,
                        ) {
                            SignInScreen(UserPreference.getInstance(dataStore))
                        }
                    }
                }
            }
        }
    }
}