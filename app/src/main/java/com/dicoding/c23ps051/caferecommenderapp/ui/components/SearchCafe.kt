package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun SearchCafe(
    navigateToSearchCafe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .height(128.dp)

    ) {
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = stringResource(id = R.string.search_text),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600
            )
            Button(
                text = stringResource(id = R.string.get_recommendation),
                onClick = navigateToSearchCafe
            )
        }
    }
}

@Preview
@Composable
fun SearchCafePreview() {
    CafeRecommenderAppTheme {
        SearchCafe({})
    }
}