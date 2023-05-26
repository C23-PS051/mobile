package com.dicoding.c23ps051.caferecommenderapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BottomBar
import com.dicoding.c23ps051.caferecommenderapp.ui.navigation.Screen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.favorite.FavoriteScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.home.HomeScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.profile.ProfileScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.recommended.RecommendedScreen

@Composable
fun CafeRecommenderApp(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Recommended.route) {
                RecommendedScreen()
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(userPreference)
            }
        }
    }
}