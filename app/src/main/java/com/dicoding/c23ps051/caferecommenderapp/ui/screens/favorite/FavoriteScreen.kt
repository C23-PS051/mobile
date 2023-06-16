package com.dicoding.c23ps051.caferecommenderapp.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackPressHandler
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeLargeList
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafesTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    navigateToDetail: (String, Boolean) -> Unit,
    userPreference: UserPreference,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(userPreference)
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

    val uiState = viewModel.uiState.collectAsState().value
    FavoriteContent(
        initial = { viewModel.getFavoritesByUserId() },
        uiState = uiState,
        onLoading = { viewModel.getFavoritesByUserId() },
        onRetry = { viewModel.getFavoritesByUserId() },
        text = query,
        onQueryChange = { newText ->
            viewModel.searchCafes(newText)
        },
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
fun FavoriteContent(
    initial: () -> Unit,
    uiState: UiState<List<Cafe>>,
    onLoading: () -> Unit,
    onRetry: () -> Unit,
    text: String,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (String, Boolean) -> Unit,
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
                title = stringResource(id = R.string.your_favorites),
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
            uiState.let { state ->
                when (state) {
                    UiState.Initial -> { initial() }
                    UiState.Loading -> {
                        Box(
                            modifier = Modifier
                            .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingScreen(stringResource(id = R.string.loading_favorites))
                        }
                        onLoading()
                    }

                    is UiState.Success -> {
                        if (state.data.isNotEmpty()) {
                            CafeLargeList(
                                fromFavorite = true,
                                cafes = state.data,
                                onCafeItemClick = navigateToDetail,
                                state = listState
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(id = R.string.this_list_is_empty))
                            }
                        }
                    }

                    is UiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.failed_to_load_cafe),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            ButtonSmall(text = stringResource(id = R.string.retry)) {
                                onRetry()
                            }
                        }
                    }
                }
            }
        }
    }
}