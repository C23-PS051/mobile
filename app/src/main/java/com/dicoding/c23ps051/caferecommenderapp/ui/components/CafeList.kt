package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.model.CafeDummy

@Composable
fun CafeList(
    modifier: Modifier = Modifier
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        val cafes = CafeDummy.cafelist
        items(cafes.size) { i ->
            CafeItem(
                thumbnail = cafes[i].thumbnail,
                name = cafes[i].name,
                rating = cafes[i].rating,
            )
        }
    }
}