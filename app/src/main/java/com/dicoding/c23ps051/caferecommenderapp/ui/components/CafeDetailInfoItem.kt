package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun CafeDetailInfoItem(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp)
        )
        Text(text)
    }
}

@Composable
fun CafeDetailInfoItem(
    icon: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(text)
    }
}