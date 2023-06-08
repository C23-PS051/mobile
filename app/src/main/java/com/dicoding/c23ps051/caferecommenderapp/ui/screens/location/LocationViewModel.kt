package com.dicoding.c23ps051.caferecommenderapp.ui.screens.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.PermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class LocationViewModel(private val pref: UserPreference) : ViewModel() {

    private var _locationPermission = MutableLiveData<PermissionState>(PermissionState.Initial)
    val locationPermission: LiveData<PermissionState> get() = _locationPermission

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
                viewModelScope.launch {
                if (location != null) {
                        val locationName = retrievePlaceName(context, location.latitude, location.longitude)
                        if (locationName == context.getString(R.string.unknown)) {
                            val errorMessage = "Failed to get user location."
                            _locationPermission.value = PermissionState.Failed(errorMessage)
                        } else {
                            _locationPermission.value = PermissionState.Granted(locationName)
                        }
                    } else {
                        val errorMessage = "Failed to get user location."
                        _locationPermission.value = PermissionState.Failed(errorMessage)
                    }
                }
            }
            fusedLocationClient.lastLocation.addOnFailureListener { exception ->
                _locationPermission.value = exception.message?.let { PermissionState.Failed("$it.") }
            }
        } else {
            _locationPermission.value = PermissionState.NotGranted
        }
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun setPermissionToInitial() {
        viewModelScope.launch {
            _locationPermission.value = PermissionState.Initial
        }
    }

    fun setPermissionToNotGranted() {
        viewModelScope.launch {
            _locationPermission.value = PermissionState.NotGranted
        }
    }

    fun setLocationTo(location: String) {
        viewModelScope.launch {
            _locationPermission.value = PermissionState.Granted(location)
        }
    }

    fun saveLocation(location: String) {
        viewModelScope.launch {
            pref.setUserLocation(location)
        }
    }
}