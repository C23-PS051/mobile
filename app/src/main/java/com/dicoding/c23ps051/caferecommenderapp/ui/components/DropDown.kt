package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun OutlinedDropDown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    options: List<String>,
    actions: List<() -> Unit>,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { /*TODO*/ },
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Gray,
                shape = RoundedCornerShape(8.dp),
            ),
    ) {
        options.indices.forEach { i ->
            DropdownMenuItem(
                content = { Text(options[i]) },
                onClick = { actions[i]() }
            )
        }
    }
}