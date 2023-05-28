package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
) {
    AssistChip(
        onClick = { /*TODO*/ },
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
        },
        modifier = modifier
            .height(28.dp),
        border = AssistChipDefaults.assistChipBorder(MaterialTheme.colorScheme.outline)
    )
}