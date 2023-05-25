package com.dicoding.c23ps051.caferecommenderapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BottomBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafesTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

class RecommendedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Recommended()
                }
            }
        }
    }
}

@Composable
fun Recommended(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { CafesTopBar(title = stringResource(id = R.string.recommended_for_you), showBackButton = true) },
        bottomBar = { BottomBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = APP_CONTENT_PADDING),
        ) {

        }
    }
}

@Preview
@Composable
fun RecommendedPreview() {
    CafeRecommenderAppTheme {
        Recommended()
    }
}