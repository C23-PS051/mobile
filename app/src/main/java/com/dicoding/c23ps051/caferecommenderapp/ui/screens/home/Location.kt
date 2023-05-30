package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationRequest
import java.util.Locale

@Composable
fun Location() {
    val locationManager = (LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    val geocoder = Geocoder(LocalContext.current, Locale.getDefault())

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 1000
                }

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } else {
                /*TODO*/
            }
        }
    )

    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var currentPlaceName by remember { mutableStateOf("") }
}