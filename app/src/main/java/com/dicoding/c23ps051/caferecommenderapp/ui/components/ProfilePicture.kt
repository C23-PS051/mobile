package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun ProfilePicture(
    image: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = if (image == "") {
            painterResource(id = R.drawable.profile)
        } else {
            rememberImagePainter(
                data = Uri.parse(image),
                builder = { scale(Scale.FILL) }
            )
       },
        contentDescription = stringResource(id = R.string.your_profile_picture),
        modifier = modifier.size(144.dp).clip(CircleShape),
    )
}