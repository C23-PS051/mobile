package com.dicoding.c23ps051.caferecommenderapp.ui.screen.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BottomBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Header
import com.dicoding.c23ps051.caferecommenderapp.ui.components.HomeSection
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SearchCafe
import com.dicoding.c23ps051.caferecommenderapp.ui.components.WelcomeText
import com.dicoding.c23ps051.caferecommenderapp.ui.navigation.Screen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.favorite.FavoriteScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.profile.ProfileScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.recommended.RecommendedScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { Header() },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)

        ) {
            Spacer(modifier = Modifier.height(8.dp))
//            WelcomeText("Yen")
            SearchCafe()
            Column (
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                HomeSection(title = stringResource(id = R.string.nearby))
                HomeSection(title = stringResource(id = R.string.open_24_hours))
                HomeSection(title = stringResource(id = R.string.on_budget))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CafeRecommenderAppTheme {
        HomeScreen()
    }
}