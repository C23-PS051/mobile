package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe

@Composable
fun CafeList(
    onCafeItemClick: (String) -> Unit,
    cafes: List<Cafe>,
    modifier: Modifier = Modifier,
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        itemsIndexed(cafes) { _, cafe ->
            CafeItem(
                id = cafe.id,
                thumbnail = cafe.thumbnail,
                name = cafe.name,
                rating = cafe.rating,
                review = cafe.review,
                onClick = onCafeItemClick
            )
        }
    }
}