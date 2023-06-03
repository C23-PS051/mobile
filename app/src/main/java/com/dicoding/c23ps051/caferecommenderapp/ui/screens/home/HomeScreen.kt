package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackPressHandler
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Header
import com.dicoding.c23ps051.caferecommenderapp.ui.components.HomeSection
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SearchCafe
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen

@Composable
fun HomeScreen(
    userPreference: UserPreference,
    navigateToDetail: (Long) -> Unit,
    navigateToSearchCafe: () -> Unit,
    onProfileClick: () -> Unit,
    userLocation: String?,
    viewModel: HomeViewModel = viewModel(
        factory = RepositoryViewModelFactory(Injection.provideRepository())
    ),
    preferenceViewModel: PreferenceViewModel = viewModel(factory = PreferenceViewModelFactory(userPreference)),
) {
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    var nearbyCafes = emptyList<Cafe>()
    var open24HoursCafes = emptyList<Cafe>()
    var onBudgetCafes = emptyList<Cafe>()
    var photoUrl = ""

    viewModel.uiStateNearby.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.exploring_cafe_options))
                viewModel.getNearbyCafes()
            }
            is UiState.Success -> {
                nearbyCafes = uiState.data
                notifyHomeContent(
                    onProfileClick = onProfileClick,
                    nearbyCafes = nearbyCafes,
                    open24HoursCafes = open24HoursCafes,
                    onBudgetCafes = onBudgetCafes,
                    navigateToDetail = navigateToDetail,
                    navigateToSearchCafe = navigateToSearchCafe,
                    userLocation = userLocation,
                    photoUrl = photoUrl
                )
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    viewModel.uiStateOpen24Hours.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.exploring_cafe_options))
                viewModel.getOpen24HoursCafes()
            }
            is UiState.Success -> {
                open24HoursCafes = uiState.data
                notifyHomeContent(
                    onProfileClick = onProfileClick,
                    nearbyCafes = nearbyCafes,
                    open24HoursCafes = open24HoursCafes,
                    onBudgetCafes = onBudgetCafes,
                    navigateToDetail = navigateToDetail,
                    navigateToSearchCafe = navigateToSearchCafe,
                    userLocation = userLocation,
                    photoUrl = photoUrl
                )
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    viewModel.uiStateOnBudget.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.exploring_cafe_options))
                viewModel.getOnBudgetCafes()
            }
            is UiState.Success -> {
                onBudgetCafes = uiState.data
                notifyHomeContent(
                    onProfileClick = onProfileClick,
                    nearbyCafes = nearbyCafes,
                    open24HoursCafes = open24HoursCafes,
                    onBudgetCafes = onBudgetCafes,
                    navigateToDetail = navigateToDetail,
                    navigateToSearchCafe = navigateToSearchCafe,
                    userLocation = userLocation,
                    photoUrl = photoUrl
                )
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    preferenceViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                preferenceViewModel.getLogin()
            }
            is UiState.Success -> {
                photoUrl = uiState.data.photoUrl
                notifyHomeContent(
                    onProfileClick = onProfileClick,
                    nearbyCafes = nearbyCafes,
                    open24HoursCafes = open24HoursCafes,
                    onBudgetCafes = onBudgetCafes,
                    navigateToDetail = navigateToDetail,
                    navigateToSearchCafe = navigateToSearchCafe,
                    userLocation = userLocation,
                    photoUrl = photoUrl
                )
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }

    // User needs to press back twice to exit
    BackPressHandler(
        backPressState = backPressState,
        toggleBackPressState = {
            backPressState = if (backPressState == BackPress.Idle) {
                BackPress.InitialTouch
            } else {
                BackPress.Idle
            }
        }
    ) {
        Toast.makeText(context, context.getText(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun notifyHomeContent(
    onProfileClick: () -> Unit,
    nearbyCafes: List<Cafe>,
    open24HoursCafes: List<Cafe>,
    onBudgetCafes: List<Cafe>,
    navigateToDetail: (Long) -> Unit,
    navigateToSearchCafe: () -> Unit,
    userLocation: String?,
    photoUrl: String,
) {
    if (nearbyCafes.isNotEmpty() && open24HoursCafes.isNotEmpty() && onBudgetCafes.isNotEmpty()) {
        HomeContent(
            navigateToDetail = navigateToDetail,
            navigateToSearchCafe = navigateToSearchCafe,
            onProfileClick = onProfileClick,
            nearbyCafes = nearbyCafes,
            open24HoursCafes = open24HoursCafes,
            onBudgetCafes = onBudgetCafes,
            userLocation = userLocation,
            photoUrl = photoUrl
        )
    }
}

@Composable
fun HomeContent(
    navigateToDetail: (Long) -> Unit,
    navigateToSearchCafe: () -> Unit,
    onProfileClick: () -> Unit,
    nearbyCafes: List<Cafe>,
    open24HoursCafes: List<Cafe>,
    onBudgetCafes: List<Cafe>,
    userLocation: String?,
    photoUrl: String,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { Header(userLocation, photoUrl, onProfileClick) },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)

        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SearchCafe(navigateToSearchCafe = navigateToSearchCafe)
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