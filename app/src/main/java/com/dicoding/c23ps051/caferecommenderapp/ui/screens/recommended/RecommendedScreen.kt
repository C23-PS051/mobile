package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryPreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackPressHandler
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeLargeList
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafesTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun RecommendedScreen(
    navigateToDetail: (Long) -> Unit,
    userPreference: UserPreference,
    viewModel: RecommendedViewModel = viewModel(
        factory = RepositoryPreferenceViewModelFactory(Injection.provideRepository(), userPreference)
    ),
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    var isFirstIndexVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }

    val query by viewModel.query
    val expanded by viewModel.expanded
    val selectedText by viewModel.selectedText
    val regionChip by viewModel.regionChip
    val openChip by viewModel.openChip

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.loading_recommendations))
                viewModel.getAllRecommendedCafes()
                viewModel.searchCafes(query)
            }
            is UiState.Success -> {
                RecommendedContent(
                    text = query,
                    onQueryChange = { newText ->
                        viewModel.searchCafes(newText)
                    },
                    cafes = uiState.data,
                    navigateToDetail = navigateToDetail,
                    focusManager = focusManager,
                    listState = listState,
                    expanded = expanded,
                    selectedText = selectedText,
                    setExpandedState = { viewModel.setExpandedState(it) },
                    setSelectedTextState = { viewModel.setSelectedTextState(it) },
                    onDismissRequest = { viewModel.setExpandedState(false) },
                    isRegionChipChecked = regionChip,
                    onRegionChipClicked = { viewModel.setRegionChipState(!regionChip) },
                    isOpenChipChecked = openChip,
                    onOpenChipClicked = { viewModel.setOpenChipState(!openChip) },
                )
            }
            is UiState.Error -> {
                /* TODO */
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { firstVisibleItemIndex ->
                isFirstIndexVisible = firstVisibleItemIndex == 0
            }
    }

    // When there is query on the search bar or user has scrolled down the list or state is not the same as initial
    // Then handle the back press differently: Clear the search bar and scroll back to the top
    if (query != "" || !isFirstIndexVisible || !viewModel.isDefaultStates()) {
        backPressState = BackPress.Idle
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
            viewModel.getAllRecommendedCafes()
            viewModel.searchCafes("")
            focusManager.clearFocus()
            viewModel.resetStates()
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }
}

@Composable
fun RecommendedContent(
    text: String,
    onQueryChange: (String) -> Unit,
    cafes: List<Cafe>,
    navigateToDetail: (Long) -> Unit,
    focusManager: FocusManager,
    listState: LazyListState,
    selectedText: Int,
    expanded: Boolean,
    setSelectedTextState: (Int) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    isRegionChipChecked: Boolean,
    onRegionChipClicked: () -> Unit,
    isOpenChipChecked: Boolean,
    onOpenChipClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CafesTopBar(
                text = text,
                onQueryChange = onQueryChange,
                title = stringResource(id = R.string.recommended_for_you),
                showBackButton = false,
                focusManager = focusManager,
                expanded = expanded,
                selectedText = selectedText,
                setExpandedState = setExpandedState,
                setSelectedTextState = setSelectedTextState,
                onDismissRequest = onDismissRequest,
                isRegionChipChecked = isRegionChipChecked,
                onRegionChipClicked = onRegionChipClicked,
                isOpenChipChecked = isOpenChipChecked,
                onOpenChipClicked = onOpenChipClicked,
            )
         },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = APP_CONTENT_PADDING),
        ) {
            CafeLargeList(
                cafes = cafes,
                onCafeItemClick = navigateToDetail,
                state = listState
            )
        }
    }
}