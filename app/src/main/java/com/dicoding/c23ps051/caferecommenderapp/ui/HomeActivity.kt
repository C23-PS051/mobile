package com.dicoding.c23ps051.caferecommenderapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.BottomBarItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Header
import com.dicoding.c23ps051.caferecommenderapp.ui.components.HomeSection
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SearchCafe
import com.dicoding.c23ps051.caferecommenderapp.ui.components.WelcomeText
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.*

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { Header() },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            WelcomeText("Yen")
            SearchCafe()
            Spacer(modifier = Modifier.height(16.dp))
            HomeSection(title = stringResource(id = R.string.nearby))
            HomeSection(title = stringResource(id = R.string.open_24_hours))
            HomeSection(title = stringResource(id = R.string.on_budget))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CafeRecommenderAppTheme() {
        Home()
    }
}

@Composable
fun CafeList(
    modifier: Modifier = Modifier
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(5){
            CafeItem()
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    BottomNavigation (
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home
            ),
            BottomBarItem(
                title = stringResource(id = R.string.menu_recommended),
                icon = Icons.Default.ThumbUp
            ),
            BottomBarItem(
                title = stringResource(id = R.string.menu_favorite),
                icon = Icons.Default.Favorite
            ),
            BottomBarItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle
            ),
        )
        navigationItems.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(
                        text = it.title,
                        fontSize = 10.sp
                    )
                },
                selected = it.title == navigationItems[0].title,
                unselectedContentColor = Gray,
                onClick = { /*TODO*/ }
            )
        }
    }
}