package com.dicoding.c23ps051.caferecommenderapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.CafeRecommenderApp
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
            PreferenceViewModelFactory(UserPreference.getInstance(dataStore))
        )[PreferenceViewModel::class.java]

        viewModel.getLogin().observe(this) { user ->
            if (user.isLogin) {
                setContent {
                    CafeRecommenderAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background,
                        ) {
                            CafeRecommenderApp(
                                userPreference = UserPreference.getInstance(dataStore),
                                isLogin = true,
                            )
                        }
                    }
                }
            } else {
                setContent {
                    CafeRecommenderAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background,
                        ) {
                            CafeRecommenderApp(
                                userPreference = UserPreference.getInstance(dataStore),
                                isLogin = false,
                            )
                        }
                    }
                }
            }
        }
    }
}