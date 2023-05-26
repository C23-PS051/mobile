package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING

@Composable
fun StandardTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = false,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = APP_CONTENT_PADDING)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                BackButton {
                    /* TODO */
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
            TopBarTitle(title = title)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}