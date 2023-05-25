package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.model.DummyCafeLarge

@Composable
fun CafeLargeList(
    modifier: Modifier = Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        val cafes = DummyCafeLarge.cafeLargeList
        items(cafes.size) { i ->
            CafeItemLarge(
                thumbnail = cafes[i].thumbnail,
                name = cafes[i].name,
                address = cafes[i].address,
                rating = cafes[i].rating,
                distance = cafes[i].distance,
                condition = cafes[i].condition
            )
        }
    }
}