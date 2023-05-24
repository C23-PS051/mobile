package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.CafeList
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        SectionText(title, modifier)
        CafeList()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun HomeSectionPreview() {
    CafeRecommenderAppTheme {
        HomeSection(title = stringResource(id = R.string.title))
    }
}