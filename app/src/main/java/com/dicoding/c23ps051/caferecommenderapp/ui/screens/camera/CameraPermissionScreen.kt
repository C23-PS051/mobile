package com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.dicoding.c23ps051.caferecommenderapp.ui.states.PermissionState

@Composable
fun CameraPermissionScreen(
    navigateBack: () -> Unit,
    showInfoScreen: (Boolean) -> Unit,
    cameraSettings: Boolean,
) {
    val context = LocalContext.current

    val cameraPermission = CAMERA
    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        READ_EXTERNAL_STORAGE
    }

    val permissionToRequest = remember { mutableListOf<String>() }
    val permissionState = remember { mutableStateOf<PermissionState>(PermissionState.Initial) }

    if (ContextCompat.checkSelfPermission(context, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
        permissionToRequest.add(cameraPermission)
    }

    if (ContextCompat.checkSelfPermission(context, imagePermission) != PackageManager.PERMISSION_GRANTED) {
        permissionToRequest.add(imagePermission)
    }

    when (permissionState.value) {
        PermissionState.Initial -> {
            showInfoScreen(false)
            if (permissionToRequest.isNotEmpty()) {
                PermissionHandler(
                    onPermissionGranted = {
                        permissionState.value = PermissionState.Granted("")
                    },
                    onPermissionDenied = {
                        permissionState.value = PermissionState.NotGranted
                    },
                    permissions = permissionToRequest.toTypedArray()
                )
            } else {
                permissionState.value = PermissionState.Granted("")
            }
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
fun PermissionHandler(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    permissions: Array<String>
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.all { it.value }
        if (allGranted) onPermissionGranted()
        else onPermissionDenied()
    }

    LaunchedEffect(Unit) {
        launcher.launch(permissions)
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
