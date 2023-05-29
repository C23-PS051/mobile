package com.dicoding.c23ps051.caferecommenderapp.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FavoriteButton

@Composable
fun DetailScreen(
    itemId: Int,
    navigateBack: () -> Unit,

) {
    DetailContent(toggleFavorite = { /*TODO*/ }, isFavorite = false, navigateBack = navigateBack)
}

@Composable
fun DetailContent(
    toggleFavorite: () -> Unit,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Image of ...",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        FavoriteButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            onClick = toggleFavorite,
            isFavorite = isFavorite,
        )
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            onClick = navigateBack,
            showBackground = true
        )
    }
}