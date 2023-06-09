package com.dicoding.c23ps051.caferecommenderapp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up?email={email}&name={name}&photo_url={photo_url}") {
        fun createRoute(email: String?, name: String?, photoUrl: String?) =
            "sign_up?email=$email&name=$name&photo_url=$photoUrl"
    }
    object Home : Screen("home")
    object Recommended : Screen("recommended")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
    object Search : Screen("search")
}
