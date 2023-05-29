package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeLargeList
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafesTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.home.HomeViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun RecommendedScreen(
    navigateToDetail: (Long) -> Unit,
    viewModel: RecommendedViewModel = viewModel(
        factory = RepositoryViewModelFactory(Injection.provideRepository())
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRecommendedCafes()
            }
            is UiState.Success -> {
                RecommendedContent(
                    cafes = uiState.data,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                /* TODO */
            }
        }
    }
}

@Composable
fun RecommendedContent(
    cafes: List<Cafe>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { CafesTopBar(title = stringResource(id = R.string.recommended_for_you), showBackButton = false) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = APP_CONTENT_PADDING),
        ) {
            CafeLargeList(
                cafes = cafes,
                onCafeItemClick = navigateToDetail
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