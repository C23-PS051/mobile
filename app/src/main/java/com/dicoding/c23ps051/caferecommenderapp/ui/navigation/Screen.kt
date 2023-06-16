package com.dicoding.c23ps051.caferecommenderapp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up?email={email}&name={name}&photo_url={photo_url}")
    object Home : Screen("home")
    object Recommended : Screen("recommended")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("detail/{id}/{from_favorite}") {
        fun createRoute(id: String, fromFavorite: Boolean) = "detail/$id/$fromFavorite"
    }
    object Search : Screen("search")
    object EditProfile : Screen("edit_profile")
}
