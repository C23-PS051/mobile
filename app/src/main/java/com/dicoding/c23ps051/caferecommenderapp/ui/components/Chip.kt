package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.White

@Composable
fun Chip(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                color = if (isChecked) White else MaterialTheme.colorScheme.outline,
                fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
            )
        },
        modifier = modifier
            .height(28.dp),
        border = if (isChecked) {
            AssistChipDefaults.assistChipBorder(
                borderColor = MaterialTheme.colorScheme.secondary,
                borderWidth = 2.dp
            )
        } else {
            AssistChipDefaults.assistChipBorder(
                borderColor = MaterialTheme.colorScheme.outline,
                borderWidth = 1.dp
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isChecked) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
        )
    )
}