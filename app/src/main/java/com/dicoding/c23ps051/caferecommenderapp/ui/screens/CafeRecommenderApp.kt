package com.dicoding.c23ps051.caferecommenderapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BottomBar
import com.dicoding.c23ps051.caferecommenderapp.ui.navigation.Screen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail.DetailScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.favorite.FavoriteScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.home.HomeScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile.ProfileScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended.RecommendedScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in.SignInScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up.SignUpScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.welcome.WelcomeScreen

@Composable
fun CafeRecommenderApp(
    userPreference: UserPreference,
    isLogin: Boolean,
    userLocation: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { if (isLogin) BottomBar(navController) },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLogin) Screen.Home.route else Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    userPreference = userPreference,
                    onProfileClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.Profile.route)
                    },
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    userLocation = userLocation
                )
            }
            composable(Screen.Recommended.route) {
                RecommendedScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    userPreference = userPreference,
                    navigateToPrivacyPolicy = {

                    },
                    navigateToHelpCenter = {

                    },
                    navigateToApplicationInfo = {

                    }
                )
            }
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    navigateToSignIn = {
                        navController.navigate(Screen.SignIn.route)
                    },
                    navigateToSignUp = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )
            }
            composable(Screen.SignIn.route) {
                SignInScreen(
                    userPreference = userPreference,
                    navigateToSignUp = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignUp.route)
                    },
                    navigateUp = {
                        navController.navigateUp()
                    },
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    navigateToSignIn = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignIn.route)
                    },
                    navigateUp = {
                        navController.navigateUp()
                    },
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") {  type = NavType.LongType})
            ) {
                val id = it.arguments?.getLong("id") ?: -1
                DetailScreen(
                    itemId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}