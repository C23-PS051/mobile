package com.dicoding.c23ps051.caferecommenderapp.ui.screens.location

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.FAILED
import com.dicoding.c23ps051.caferecommenderapp.constants.NOT_GRANTED
import com.dicoding.c23ps051.caferecommenderapp.constants.UNKNOWN
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel : ViewModel() {

    private val _location: MutableLiveData<String?> = MutableLiveData(UNKNOWN)
    val location: LiveData<String?> get() = _location

    private fun retrievePlaceName(context: Context, lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val area =  address.subAdminArea
                return area ?: context.getString(R.string.unknown)
            }
        }
        return context.getString(R.string.unknown)
    }

    fun getUserLocation(context: Context) {
        if (checkPermission(context, ACCESS_FINE_LOCATION) &&
            checkPermission(context, ACCESS_COARSE_LOCATION)
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d("MyLogger", "${location.latitude}, ${location.longitude}")
                    _location.value = retrievePlaceName(context, location.latitude, location.longitude)
                } else {
                    _location.value = FAILED
                }
            }
        } else {
            setLocationToNotGranted()
        }
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun setLocationToNull() {
        _location.value = null
    }

    fun setLocationToNotGranted() {
        _location.value = NOT_GRANTED
    }

    fun setLocationToUnknown() {
        _location.value = UNKNOWN
    }
}