package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Header
import com.dicoding.c23ps051.caferecommenderapp.ui.components.HomeSection
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SearchCafe
import com.dicoding.c23ps051.caferecommenderapp.ui.components.WelcomeText
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun HomeScreen(
    navigateToDetail: (Long) -> Unit,
    userLocation: String,
    viewModel: HomeViewModel = viewModel(
        factory = RepositoryViewModelFactory(Injection.provideRepository())
    ),
) {
    var nearbyCafes = emptyList<Cafe>()
    var open24HoursCafes = emptyList<Cafe>()
    var onBudgetCafes = emptyList<Cafe>()

    viewModel.uiStateNearby.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getNearbyCafes()
            }
            is UiState.Success -> {
                nearbyCafes = uiState.data
                notifyHomeContent(nearbyCafes, open24HoursCafes, onBudgetCafes, navigateToDetail, userLocation)
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    viewModel.uiStateOpen24Hours.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getOpen24HoursCafes()
            }
            is UiState.Success -> {
                open24HoursCafes = uiState.data
                notifyHomeContent(nearbyCafes, open24HoursCafes, onBudgetCafes, navigateToDetail, userLocation)
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    viewModel.uiStateOnBudget.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getOnBudgetCafes()
            }
            is UiState.Success -> {
                onBudgetCafes = uiState.data
                notifyHomeContent(nearbyCafes, open24HoursCafes, onBudgetCafes, navigateToDetail, userLocation)
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

}

@Composable
fun notifyHomeContent(
    nearbyCafes: List<Cafe>,
    open24HoursCafes: List<Cafe>,
    onBudgetCafes: List<Cafe>,
    navigateToDetail: (Long) -> Unit,
    userLocation: String,
) {
    if (nearbyCafes.isNotEmpty() && open24HoursCafes.isNotEmpty() && onBudgetCafes.isNotEmpty()) {
        HomeContent(
            navigateToDetail = navigateToDetail,
            nearbyCafes = nearbyCafes,
            open24HoursCafes = open24HoursCafes,
            onBudgetCafes = onBudgetCafes,
            userLocation = userLocation
        )
    }
}

@Composable
fun HomeContent(
    navigateToDetail: (Long) -> Unit,
    nearbyCafes: List<Cafe>,
    open24HoursCafes: List<Cafe>,
    onBudgetCafes: List<Cafe>,
    userLocation: String,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { Header(userLocation) },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)

        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SearchCafe()
            Column (
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
//                WelcomeText("Yen", modifier = Modifier.align(Alignment.CenterHorizontally))
                HomeSection(
                    title = stringResource(id = R.string.nearby),
                    onCafeItemClick = navigateToDetail,
                    cafes = nearbyCafes,
                )
                HomeSection(
                    title = stringResource(id = R.string.open_24_hours),
                    onCafeItemClick = navigateToDetail,
                    cafes = open24HoursCafes,
                )
                HomeSection(
                    title = stringResource(id = R.string.on_budget),
                    onCafeItemClick = navigateToDetail,
                    cafes = onBudgetCafes,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    CafeRecommenderAppTheme {
//        HomeScreen(onCafeItemClick = {})
//    }
//}