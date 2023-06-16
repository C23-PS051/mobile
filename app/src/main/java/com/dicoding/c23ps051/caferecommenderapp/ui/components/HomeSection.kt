package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.CafeDummy
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun HomeSection(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(bottom = 16.dp)) {
        SectionText(title, modifier)
        content()
    }
}

@Preview
@Composable
fun HomeSectionPreview() {
    CafeRecommenderAppTheme {
        HomeSection(
            title = stringResource(id = R.string.title),
            content = {
                CafeList(
                    fromFavorite = false,
                    onCafeItemClick = { _, _ -> },
                    cafes = CafeDummy.cafeList,
                )
            }
        )
    }
}