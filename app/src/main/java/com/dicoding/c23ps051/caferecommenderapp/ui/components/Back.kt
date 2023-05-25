package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun Back(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = stringResource(id = R.string.back_button),
        Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Preview
@Composable
fun BackPreview() {
    CafeRecommenderAppTheme {
        Back(
            onClick = {
                /* TODO */
            }
        )
    }
}