package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = stringResource(id = R.string.app_logo),
        modifier = modifier
            .width(196.dp),
    )
}