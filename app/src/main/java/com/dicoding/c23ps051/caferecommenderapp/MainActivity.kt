package com.dicoding.c23ps051.caferecommenderapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.constants.FAILED
import com.dicoding.c23ps051.caferecommenderapp.constants.NOT_GRANTED
import com.dicoding.c23ps051.caferecommenderapp.constants.UNKNOWN
import com.dicoding.c23ps051.caferecommenderapp.constants.REGIONS
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.CafeRecommenderApp
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.InfoScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended.SearchScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: PreferenceViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var newUserObserver: Observer<Boolean>

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
                    when (locationViewModel.location.value) {
                        UNKNOWN -> {
                            if (checkPermission(ACCESS_FINE_LOCATION) &&
                                checkPermission(ACCESS_COARSE_LOCATION)) {
                                getUserLocation()
                            } else {
                                setComposable {
                                    LocationScreen(
                                        onButtonClick = {
                                            getUserLocation()
                                        }
                                    )
                                }
                            }
                        }

                        FAILED -> {
                            setComposable {
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
                        }

                        NOT_GRANTED -> {
                            setComposable {
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
                        }

                        else -> {
                            var isValidRegion = false
                            if (locationViewModel.location.value != null) {
                                REGIONS.forEach { region ->
                                    if (locationViewModel.location.value.equals(region)) {
                                        isValidRegion = true
                                    }
                                }
                            }

                            if (isValidRegion) {
                                if (user.isNewUser) {
                                    setComposable {
                                        SearchScreen(
                                            region = locationViewModel.location.value,
                                            navigateUp = { setDefaultContent(true, locationViewModel.location.value) },
                                            onSubmit = {
                                                setDefaultContent(true, locationViewModel.location.value)
                                                viewModel.setNotNewUser()
                                            }
                                        )
                                    }
                                } else {
                                    setDefaultContent(true, locationViewModel.location.value)
                                }
                            } else {
                                setComposable {
                                    InfoScreen(
                                        text = getString(R.string.location_not_valid),
                                        actionText = getString(R.string.ok),
                                        action = {
                                            setDefaultContent(true, null)
                                        }
                                    )
                                }
                            }

//                                CafeRecommenderApp(
//                                    userPreference = UserPreference.getInstance(
//                                        dataStore
//                                    ),
//                                    isLogin = true,
//                                    userLocation = locationViewModel.location.value,
//                                )
                        }
                    }
                }
            } else {
                setDefaultContent(false, null)
            }
        }
    }

    private fun setComposable(content: @Composable () -> Unit) {
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    content()
                }
            }
        }
    }

    private fun setDefaultContent(isLogin: Boolean, userLocation: String?) {
        setComposable {
            CafeRecommenderApp(
                userPreference = UserPreference.getInstance(dataStore),
                isLogin = isLogin,
                userLocation = userLocation
            )
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