package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FacilitiesItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.inverseOnSurface,
    )
}