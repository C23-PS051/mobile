package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.CafeDummy

@Composable
fun CafeLargeList(
    cafes: List<Cafe>,
    onCafeItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(cafes) { _, cafe ->
            CafeItemLarge(
                id = cafe.id,
                thumbnail = cafe.thumbnail,
                name = cafe.name,
                address = cafe.address,
                rating = cafe.rating,
                ratingCount = cafe.ratingCount,
                distance = cafe.distance,
                condition = cafe.isOpen,
                onClick = onCafeItemClick
            )
        }
    }
}