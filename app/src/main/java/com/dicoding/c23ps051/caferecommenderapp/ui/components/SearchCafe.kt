package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun SearchCafe(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = modifier
//            .padding(16.dp)
//            .background(MaterialTheme.colors.secondary)
            .clip(RoundedCornerShape(12.dp))
            .height(110.dp)

    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.padding(16.dp)
        ){
            Text(
                text = stringResource(id = R.string.search_text),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.W600
            )
            FilledSearchBar()
        }
    }
}

@Preview
@Composable
fun SearchCafePreview() {
    CafeRecommenderAppTheme {
        SearchCafe()
    }
}