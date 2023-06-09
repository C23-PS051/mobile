package com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.PermissionState

@Composable
fun CameraPermissionScreen(
    navigateBack: () -> Unit,
    showInfoScreen: (Boolean) -> Unit,
    cameraSettings: Boolean,
) {
    val cameraPermissionState = remember { mutableStateOf<PermissionState>(PermissionState.Initial) }

    when (cameraPermissionState.value) {
        PermissionState.Initial -> {
            showInfoScreen(false)
            CameraPermissionHandler(
                onPermissionGranted = {
                    cameraPermissionState.value = PermissionState.Granted("")
                },
                onPermissionDenied = {
                    cameraPermissionState.value = PermissionState.NotGranted
                },
            )
        }
        PermissionState.NotGranted -> {
            showInfoScreen(true)
        }
        is PermissionState.Granted -> {
            showInfoScreen(false)
            navigateBack()
        }
        else -> navigateBack()
    }

    if (cameraSettings) {
        CameraPermissionSettingsLauncher {
            navigateBack()
            showInfoScreen(false)
        }
    }
}

@Composable
fun CameraPermissionHandler(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onPermissionGranted()
        else onPermissionDenied()
    }

    LaunchedEffect(Unit) {
        launcher.launch(CAMERA)
    }
}

@Composable
fun CameraPermissionSettingsLauncher(
    onActivityResult: () -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        onActivityResult()
    }

    LaunchedEffect(Unit) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        launcher.launch(intent)
    }
}
