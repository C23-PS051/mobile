package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .background(colorResource(id = R.color.white))
            .padding(8.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
            )
            Text(
                text = stringResource(id = R.string.search_hint),
                color = colorResource(id = R.color.gray),
                modifier = modifier.padding(horizontal = 4.dp).weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    CafeRecommenderAppTheme {
        SearchBar()
    }
}