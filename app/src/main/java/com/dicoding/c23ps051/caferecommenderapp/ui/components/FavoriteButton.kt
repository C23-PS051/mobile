package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Black66A

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isFavorite: Boolean,
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier.clip(CircleShape).background(Black66A),
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = stringResource(id = R.string.add_to_favorite),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}