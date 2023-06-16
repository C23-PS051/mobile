package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING

@Composable
fun StandardTopBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBack: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(APP_CONTENT_PADDING),
        contentAlignment = Alignment.Center
    ) {
        if (showBackButton) {
            BackButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        TopBarTitle(title = title)
    }
}