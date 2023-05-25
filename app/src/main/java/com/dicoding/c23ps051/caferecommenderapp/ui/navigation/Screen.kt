package com.dicoding.c23ps051.caferecommenderapp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object Home : Screen("home")
    object Recommended : Screen("recommended")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
}
