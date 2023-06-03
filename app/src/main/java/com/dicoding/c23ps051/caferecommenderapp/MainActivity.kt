package com.dicoding.c23ps051.caferecommenderapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.constants.FAILED
import com.dicoding.c23ps051.caferecommenderapp.constants.NOT_GRANTED
import com.dicoding.c23ps051.caferecommenderapp.constants.UNKNOWN
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.CafeRecommenderApp
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.ErrorScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.InfoScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: PreferenceViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel = ViewModelProvider(
            this,
            PreferenceViewModelFactory(UserPreference.getInstance(dataStore))
        )[PreferenceViewModel::class.java]

        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        viewModel.getLoginAsLiveData().observe(this) { user ->
            if (user.isLogin) {
                locationViewModel.location.observe(this) {
                    setContent {
                        CafeRecommenderAppTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background,
                            ) {
                                when (locationViewModel.location.value) {
                                    UNKNOWN -> {
                                        if (checkPermission(ACCESS_FINE_LOCATION) &&
                                            checkPermission(ACCESS_COARSE_LOCATION)) {
                                            getUserLocation()
                                        } else {
                                            LocationScreen(
                                                onButtonClick = {
                                                    getUserLocation()
                                                }
                                            )
                                        }
                                    }

                                    FAILED -> {
                                        InfoScreen(
                                            text = getString(R.string.get_location_failed),
                                            actionText = getString(R.string.retry),
                                            secondaryActionText = getString(R.string.skip),
                                            action = {
                                                getUserLocation()
                                            },
                                            secondaryAction = {
                                                locationViewModel.setLocationToNull()
                                            }
                                        )
                                    }

                                    NOT_GRANTED -> {
                                        InfoScreen(
                                            text = getString(R.string.location_permission_not_granted),
                                            actionText = getString(R.string.go_to_settings),
                                            secondaryActionText = getString(R.string.skip),
                                            action = {
                                                val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                                                startActivityForResult(intent, REQUEST_LOCATION_SETTINGS)
                                            },
                                            secondaryAction = {
                                                locationViewModel.setLocationToNull()
                                            }
                                        )
                                    }

                                    else -> {
                                        CafeRecommenderApp(
                                            userPreference = UserPreference.getInstance(
                                                dataStore
                                            ),
                                            isLogin = true,
                                            userLocation = locationViewModel.location.value,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                setContent {
                    CafeRecommenderAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background,
                        ) {
                            CafeRecommenderApp(
                                userPreference = UserPreference.getInstance(dataStore),
                                isLogin = false,
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_LOCATION_SETTINGS) {
            locationViewModel.setLocationToUnknown()
        }
    }

    private val requestForPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[ACCESS_FINE_LOCATION] ?: false -> {
                    locationViewModel.getUserLocation(this)
                }
                permissions[ACCESS_COARSE_LOCATION] ?: false -> {
                    locationViewModel.getUserLocation(this)
                }
                else -> {
                    locationViewModel.setLocationToNotGranted()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getUserLocation() {
        if (checkPermission(ACCESS_FINE_LOCATION) &&
            checkPermission(ACCESS_COARSE_LOCATION)
        ) {
            locationViewModel.getUserLocation(this)
        } else {
            requestForPermissionLauncher.launch(
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    companion object {
        private const val REQUEST_LOCATION_SETTINGS = 100
    }
}