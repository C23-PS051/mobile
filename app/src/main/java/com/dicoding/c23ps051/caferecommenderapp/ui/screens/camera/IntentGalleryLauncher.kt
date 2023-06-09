package com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun IntentGalleryLauncher(
    postImage: (Uri) -> Unit,
    navigateBack: () -> Unit,
) {
    val launcherIntentGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            postImage(selectedImg)
        } else {
            navigateBack()
        }
    }

    val intent = Intent()
    intent.apply {
        action = Intent.ACTION_GET_CONTENT
        type = "image/*"
    }
    val chooser = Intent.createChooser(intent, stringResource(R.string.choose_gallery))

    LaunchedEffect(Unit) {
        launcherIntentGallery.launch(chooser)
    }
}