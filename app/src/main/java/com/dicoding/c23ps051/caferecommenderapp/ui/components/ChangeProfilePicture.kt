package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun ChangeProfilePicture(
    imageUrl: String,
    onTextClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    imageSize: Int = 112,
) {
    ProfilePicture(
        image = imageUrl,
        modifier = modifier.size(imageSize.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
    ClickableText(
        stringResource(id = R.string.change_profile_picture),
        onClick = onTextClick,
        modifier = modifier,
        enabled = enabled
    )
}