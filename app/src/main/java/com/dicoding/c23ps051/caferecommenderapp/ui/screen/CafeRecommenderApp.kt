package com.dicoding.c23ps051.caferecommenderapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.sign_in.SignInScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.sign_up.SignUpScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.welcome.WelcomeScreen

@Composable
fun CafeRecommenderApp(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    isLogin: Boolean,
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
                SignInScreen(userPreference,
                    navigateToSignUp = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignUp.route)
                    },
                    navigateUp = {
                        navController.navigateUp()
                    }
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
        }
    }
}