package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun OutlinedDropDown(
    expanded: Boolean,
    options: List<String>,
    actions: List<() -> Unit>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Gray,
                shape = RoundedCornerShape(8.dp),
            ),
    ) {
        options.indices.forEach { i ->
            DropdownMenuItem(
                text = { Text(options[i]) },
                onClick = { actions[i]() }
            )
        }
    }
}