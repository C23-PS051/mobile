package com.dicoding.c23ps051.caferecommenderapp.ui.screens.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button

@Composable
fun InfoScreen(
    text: String,
    image: Painter,
    actionText: String,
    action: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryActionText: String = "",
    secondaryAction: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 24.dp)
        )
        Button(text = actionText, onClick = action)
        Spacer(modifier = Modifier.height(8.dp))
        if (secondaryActionText != "") {
            Button(text = secondaryActionText, onClick = secondaryAction)
        }
    }
}