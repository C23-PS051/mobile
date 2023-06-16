package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Black66A
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackground: Boolean = false,
) {
    IconButton(
        onClick = onClick,
        modifier = if (showBackground) modifier.clip(CircleShape).background(Black66A) else modifier,
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_button),
            tint = if (showBackground) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onBackground
            }
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    CafeRecommenderAppTheme {
        BackButton(
            onClick = {}
        )
    }
}