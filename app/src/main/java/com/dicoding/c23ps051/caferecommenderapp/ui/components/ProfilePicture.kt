package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.Manifest
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.DEFAULT_PHOTO_URI

@Composable
fun ProfilePicture(
    image: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var permissionGranted = false
    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    if (ContextCompat.checkSelfPermission(context, imagePermission) == PackageManager.PERMISSION_GRANTED) {
        permissionGranted = true
    }

    Image(
        painter = if (permissionGranted) {
            if (image == "") {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = Uri.parse(DEFAULT_PHOTO_URI))
                        .apply(block = fun ImageRequest.Builder.() {
                            scale(Scale.FILL)
                        }).build()
                )
            } else {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = Uri.parse(image))
                        .apply(block = fun ImageRequest.Builder.() {
                            scale(Scale.FILL)
                        }).build()
                )
            }
        } else {
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = Uri.parse(DEFAULT_PHOTO_URI))
                    .apply(block = fun ImageRequest.Builder.() {
                        scale(Scale.FILL)
                    }).build()
            )
        },
        colorFilter = if (image == "") ColorFilter.tint(MaterialTheme.colorScheme.primary) else null,
        contentDescription = stringResource(id = R.string.your_profile_picture),
        modifier = modifier
            .size(144.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}