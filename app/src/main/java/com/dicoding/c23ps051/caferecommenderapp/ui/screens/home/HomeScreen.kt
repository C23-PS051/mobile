package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.dicoding.c23ps051.caferecommenderapp.constants.DEFAULT_PHOTO_URI
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackPressHandler
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeList
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Header
import com.dicoding.c23ps051.caferecommenderapp.ui.components.HomeSection
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProgressBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SearchCafe
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState

@Composable
fun HomeScreen(
    userPreference: UserPreference,
    navigateToDetail: (String) -> Unit,
    navigateToSearchCafe: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(userPreference)
    ),
) {
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    val userLocation = viewModel.location.collectAsState(initial = stringResource(id = R.string.all)).value

    val cafesState = viewModel.cafesState.collectAsState(initial = UiState.Loading).value
    val uiStateMyRegion = viewModel.uiStateMyRegion.collectAsState(initial = UiState.Loading).value
    val uiStateOpen24Hours = viewModel.uiStateOpen24Hours.collectAsState(initial = UiState.Loading).value
    val uiStatePopular = viewModel.uiStatePopular.collectAsState(initial = UiState.Loading).value

    val cafes = if (cafesState is UiState.Success) cafesState.data else emptyList()
    val myRegionCafes = if (uiStateMyRegion is UiState.Success) uiStateMyRegion.data else emptyList()
    val open24HoursCafes = if (uiStateOpen24Hours is UiState.Success) uiStateOpen24Hours.data else emptyList()
    val popularCafes = if (uiStatePopular is UiState.Success) uiStatePopular.data else emptyList()
    val photoUri by viewModel.profileUri.collectAsState(initial = DEFAULT_PHOTO_URI)

    cafesState.let { uiState ->
        when (uiState) {
            UiState.Loading -> {
                viewModel.getAllCafes()
            }
            is UiState.Success -> {
                if (myRegionCafes.isEmpty()) {
                    viewModel.filterByRegion(uiState.data)
                }
                if (open24HoursCafes.isEmpty()) {
                    viewModel.filterByOpen24Hours(uiState.data)
                }
                if (popularCafes.isEmpty()) {
                    viewModel.filterByPopular(uiState.data)
                }
            }
            is UiState.Error -> {
                viewModel.setMyRegionCafesToError()
                viewModel.setOn24HoursCafesToError()
                viewModel.setPopularCafesToError()
            }
        }
    }

    HomeContent(
        navigateToDetail = navigateToDetail,
        navigateToSearchCafe = navigateToSearchCafe,
        onProfileClick = onProfileClick,
        myRegionCafes = uiStateMyRegion,
        open24HoursCafes = uiStateOpen24Hours,
        popularCafes = uiStatePopular,
        onMyRegionCafesRetry = { viewModel.filterByRegion(cafes) },
        onOpen24HoursCafesRetry = { viewModel.filterByOpen24Hours(cafes) },
        onPopularCafesRetry = { viewModel.filterByPopular(cafes) },
        userLocation = userLocation,
        photoUri = photoUri
    )

//    val loadCompleted = nearbyCafes.isNotEmpty() && open24HoursCafes.isNotEmpty() && onBudgetCafes.isNotEmpty() && login != null
//
//    if (loadCompleted) {
//
//    } else {
//        viewModel.getAllCafes()
//        if (uiStateMyRegion is UiState.Loading) {
//            state.loadingMyRegionCafes = true
//        }
//        if (uiStateOpen24Hours is UiState.Loading) {
//            state.loadingOn24HoursCafes = true
//        }
//        if (uiStatePopular is UiState.Loading) {
//            state.loadingPopularCafes = true
//        }
//        if (uiStateLogin is UiState.Loading) {
//            preferenceViewModel.getLogin()
//        }
//
//        if (uiStateMyRegion is UiState.Error || uiStateOpen24Hours is UiState.Error || uiStatePopular is UiState.Error) {
//            ErrorScreen(stringResource(id = R.string.failed_to_load_cafes), { /*TODO*/ })
//        } else {
//            LoadingScreen(stringResource(id = R.string.exploring_cafe_options))
//        }
//    }

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
fun HomeContent(
    navigateToDetail: (String) -> Unit,
    navigateToSearchCafe: () -> Unit,
    onProfileClick: () -> Unit,
    myRegionCafes: UiState<List<Cafe>>,
    open24HoursCafes: UiState<List<Cafe>>,
    popularCafes: UiState<List<Cafe>>,
    onMyRegionCafesRetry: () -> Unit,
    onOpen24HoursCafesRetry: () -> Unit,
    onPopularCafesRetry: () -> Unit,
    userLocation: String?,
    photoUri: String,
    modifier: Modifier = Modifier,
) {
    Log.d("MyLogger", "Home: $photoUri")
    Scaffold(
        topBar = { Header(userLocation, photoUri, onProfileClick) },
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

                HomeSection(
                    title = stringResource(id = R.string.my_region),
                    content = {
                        myRegionCafes.let { uiState ->
                            when (uiState) {
                                UiState.Loading -> { ProgressBar(modifier.size(40.dp)) }
                                is UiState.Success -> {
                                    CafeList(
                                        onCafeItemClick = navigateToDetail,
                                        cafes = uiState.data,
                                    )
                                }
                                is UiState.Error -> {
                                    ButtonSmall(text = stringResource(id = R.string.retry)) {
                                        onMyRegionCafesRetry()
                                    }
                                }
                            }
                        }
                    },
                )
                HomeSection(
                    title = stringResource(id = R.string.open_24_hours),
                    content = {
                        open24HoursCafes.let { uiState ->
                            when (uiState) {
                                UiState.Loading -> { ProgressBar(modifier.size(40.dp)) }
                                is UiState.Success -> {
                                    CafeList(
                                        onCafeItemClick = navigateToDetail,
                                        cafes = uiState.data,
                                    )
                                }
                                is UiState.Error -> {
                                    ButtonSmall(text = stringResource(id = R.string.retry)) {
                                        onOpen24HoursCafesRetry()
                                    }
                                }
                            }
                        }
                    },
                )
                HomeSection(
                    title = stringResource(id = R.string.popular),
                    content = {
                        popularCafes.let { uiState ->
                            when (uiState) {
                                UiState.Loading -> { ProgressBar(modifier.size(40.dp)) }
                                is UiState.Success -> {
                                    CafeList(
                                        onCafeItemClick = navigateToDetail,
                                        cafes = uiState.data,
                                    )
                                }
                                is UiState.Error -> {
                                    ButtonSmall(text = stringResource(id = R.string.retry)) {
                                        onPopularCafesRetry()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}