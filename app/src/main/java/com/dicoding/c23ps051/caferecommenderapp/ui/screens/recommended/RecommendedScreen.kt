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
    viewModel: RecommendedViewModel = viewModel(
        factory = RepositoryViewModelFactory(Injection.provideRepository())
    ),
    state: HomeState = rememberHomeState(expanded = false, selectedText = stringResource(id = R.string.sort_by))
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    var isFirstIndexVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val query by viewModel.query

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
                    expanded = state.expanded,
                    selectedText = state.selectedText,
                    setExpandedState = { expanded ->
                        state.expanded = expanded
                    },
                    setSelectedTextState = { selectedText ->
                        state.selectedText = selectedText
                    },
                    onDismissRequest = {
                        state.expanded = false
                    }
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

//    Log.d("MyLogger", state.expanded.toString())
//
//    if (state.expanded) {
//        BackPressHandler(
//            backPressState = BackPress.InitialTouch,
//            toggleBackPressState = {
//                backPressState = if (backPressState == BackPress.Idle) {
//                    BackPress.InitialTouch
//                } else {
//                    BackPress.Idle
//                }
//            }
//        ) {
//            state.expanded = false
//        }
//    }

    // When there is query on the search bar or user has scrolled down the list or state is not the same as initial
    // Then handle the back press differently: Clear the search bar and scroll back to the top
    if (query != "" || !isFirstIndexVisible || !state.isDefaultState()) {
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
            state.resetStates()
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }

    @Composable
    fun ScrollToTop() {
        LaunchedEffect(listState) {
            scope.launch {
                listState.scrollToItem(0)
            }
        }
    }

    // Handle search sorting
    if (state.selectedText == stringResource(id = R.string.highest_rating)) {
        viewModel.getHighestRatingCafes()
        ScrollToTop()
    }
    if (state.selectedText == stringResource(id = R.string.nearest)) {
        viewModel.getNearestCafes()
        ScrollToTop()
    }
    if (state.selectedText == stringResource(id = R.string.highest_price_range)) {
        viewModel.getHighestPriceRangeCafes()
        ScrollToTop()
    }
    if (state.selectedText == stringResource(id = R.string.lowest_price_range)) {
        viewModel.getLowestPriceRangeCafes()
        ScrollToTop()
    }
}

class HomeState(
    initialExpandedState: Boolean,
    initialSelectedTextState: String,
) {
    private val expandedState = initialExpandedState
    private val selectedTextState = initialSelectedTextState

    var expanded by mutableStateOf(expandedState)
    var selectedText by mutableStateOf(selectedTextState)

    fun resetStates() {
        expanded = expandedState
        selectedText = selectedTextState
    }

    fun isDefaultState(): Boolean {
        return expanded == expandedState && selectedText == selectedTextState
    }
}

@Composable
fun rememberHomeState(
    expanded: Boolean,
    selectedText: String,
): HomeState = remember(
    expanded, selectedText
) {
    HomeState(expanded, selectedText)
}

@Composable
fun RecommendedContent(
    text: String,
    onQueryChange: (String) -> Unit,
    cafes: List<Cafe>,
    navigateToDetail: (Long) -> Unit,
    focusManager: FocusManager,
    listState: LazyListState,
    selectedText: String,
    expanded: Boolean,
    setSelectedTextState: (String) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
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

@Preview
@Composable
fun RecommendedPreview() {
    CafeRecommenderAppTheme {
        RecommendedScreen(navigateToDetail = {})
    }
}