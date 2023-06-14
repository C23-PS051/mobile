package com.dicoding.c23ps051.caferecommenderapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BottomBar
import com.dicoding.c23ps051.caferecommenderapp.ui.navigation.Screen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail.DetailScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.favorite.FavoriteScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.home.HomeScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.edit_profile.EditProfileScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile.ProfileScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended.RecommendedScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.search.SearchScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in.SignInScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up.SignUpScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.welcome.WelcomeScreen

@Composable
fun CafeRecommenderApp(
    userPreference: UserPreference,
    isLogin: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var bottomBarState by remember { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    bottomBarState = when (navBackStackEntry?.destination?.route) {
        Screen.Home.route,
        Screen.Recommended.route,
        Screen.Favorite.route,
        Screen.Profile.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = { if (bottomBarState) BottomBar(navController) },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLogin) Screen.Home.route else Screen.Welcome.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    userPreference = userPreference,
                    onProfileClick = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    navigateToSearchCafe = {
                        navController.navigate(Screen.Search.route)
                    },
                )
            }
            composable(Screen.Recommended.route) {
                RecommendedScreen(
                    userPreference = userPreference,
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
                    navigateToEditProfile = {
                        navController.navigate(Screen.EditProfile.route)
                    },
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
            composable(route = Screen.SignUp.route) {
                SignUpScreen(
                    userPreference = userPreference,
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
                arguments = listOf(navArgument("id") {  type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("id") ?: ""
                DetailScreen(
                    userPreference = userPreference,
                    itemId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    userPreference = userPreference,
                    newUserScreen = false,
                    navigateUp = {
                        navController.navigateUp()
                    },
                    onSubmit = {
                        navController.popBackStack()
                        navController.navigate(Screen.Recommended.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.EditProfile.route) {
                EditProfileScreen(
                    userPreference = userPreference,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}