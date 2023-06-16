package com.dicoding.c23ps051.caferecommenderapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.MainViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.CafeRecommenderApp
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.InfoScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.RequestLocationScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.search.SearchScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in.SignInViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.states.PermissionState
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userPreference: UserPreference
    private lateinit var locationObserver: Observer<PermissionState>

    private lateinit var regions: Map<String, String>
    private var isLocationHandled = false
    private val permissionToRequest = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        userPreference = UserPreference.getInstance(dataStore)

        regions = mapOf(
            getString(R.string.jakarta_utara) to getString(R.string.north_jakarta),
            getString(R.string.jakarta_selatan) to getString(R.string.south_jakarta),
            getString(R.string.jakarta_barat) to getString(R.string.west_jakarta),
            getString(R.string.jakarta_timur) to getString(R.string.east_jakarta),
            getString(R.string.jakarta_pusat) to getString(R.string.central_jakarta),
        )

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        signInViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignInViewModel::class.java]

        locationViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LocationViewModel::class.java]

        mainViewModel.getLoginAsLiveData().observe(this) { user ->
            if (user.isLogin) {
                if (isLocationHandled) {
                    if (user.isNewUser) {
                        setComposable {
                            SearchScreen(
                                userPreference = userPreference,
                                newUserScreen = true,
                                navigateUp = { setDefaultContent(true) },
                                onSubmit = {
                                    mainViewModel.setNotNewUser()
                                    setDefaultContent(true)
                                },
                            )
                        }
                    } else {
                        setDefaultContent(true)
                    }
                } else {
                    handleUserLocation(user.isNewUser)
                }
            } else {
                isLocationHandled = false
                setDefaultContent(false)
            }
        }
    }

    private fun handleUserLocation(isNewUser: Boolean) {
        locationObserver = Observer { permission ->
            when (permission) {
                PermissionState.Initial -> {
                    if (!checkPermission(ACCESS_FINE_LOCATION) &&
                        !checkPermission(ACCESS_COARSE_LOCATION)
                    ) {
                        permissionToRequest.add(ACCESS_FINE_LOCATION)
                        permissionToRequest.add(ACCESS_COARSE_LOCATION)

                    }

                    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        READ_MEDIA_IMAGES
                    } else {
                        READ_EXTERNAL_STORAGE
                    }

                    if (!checkPermission(imagePermission)) {
                        permissionToRequest.add(imagePermission)
                    }

                    if (permissionToRequest.isNotEmpty()) {
                        setComposable {
                            RequestLocationScreen(
                                onButtonClick = {
                                    getUserLocation(permissionToRequest.toTypedArray())
                                }
                            )
                        }
                    } else {
                        locationViewModel.getUserLocation(this)
                    }
                }
                PermissionState.NotGranted -> {
                    setComposable {
                        InfoScreen(
                            text = getString(R.string.permission_not_granted),
                            image = painterResource(id = R.drawable.location_off),
                            actionText = getString(R.string.go_to_settings),
                            secondaryActionText = getString(R.string.skip),
                            action = {
                                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.fromParts("package", packageName, null)
                                startActivityForResult(intent, REQUEST_APPLICATION_SETTINGS)
                            },
                            secondaryAction = {
                                locationViewModel.setPermissionTo(getString(R.string.all))
                                isLocationHandled = true
                            }
                        )
                    }
                }
                is PermissionState.Failed -> {
                    val errorMessage = permission.errorMessage
                    setComposable {
                        InfoScreen(
                            text = errorMessage + " " + getString(R.string.get_location_failed),
                            image = painterResource(id = R.drawable.location_off),
                            actionText = getString(R.string.retry),
                            secondaryActionText = getString(R.string.skip),
                            action = {
                                getUserLocation(permissionToRequest.toTypedArray())
                            },
                            secondaryAction = {
                                locationViewModel.setPermissionTo(getString(R.string.all))
                                isLocationHandled = true
                            }
                        )
                    }
                }
                is PermissionState.Granted -> {
                    val isValidRegion = checkRegion(permission.data)

                    if (!isValidRegion) {
                        setComposable {
                            InfoScreen(
                                text = getString(R.string.location_not_valid),
                                image = painterResource(id = R.drawable.location_off),
                                actionText = getString(R.string.ok),
                                action = {
                                    locationViewModel.setPermissionTo(getString(R.string.all))
                                    locationViewModel.saveLocation(getString(R.string.all))
                                    removeLocationObserver()
                                }
                            )
                        }
                    } else {
                        if (isNewUser) {
                            setComposable {
                                SearchScreen(
                                    userPreference = userPreference,
                                    navigateUp = { setDefaultContent(true) },
                                    newUserScreen = true,
                                    onSubmit = {
                                        mainViewModel.setNotNewUser()
                                        setDefaultContent(true)
                                    },
                                )
                            }
                        } else {
                            setDefaultContent(true)
                        }
                        removeLocationObserver()
                    }
                    isLocationHandled = true
                }
            }
        }

        locationViewModel.locationPermission.observe(this, locationObserver)
    }

    private fun removeLocationObserver() {
        locationViewModel.locationPermission.removeObserver(locationObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_APPLICATION_SETTINGS) {
            locationViewModel.setPermissionToInitial()
        }
    }

    private fun checkRegion(location: String): Boolean {
        if (location == getString(R.string.all)) {
            locationViewModel.saveLocation(location)
            return true
        }

        regions.forEach { region ->
            if (location == region.key) {
                locationViewModel.saveLocation(region.value)
                return true
            } else if (location == region.value) {
                locationViewModel.saveLocation(region.value)
                return true
            }
        }
        return false
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

    private fun setDefaultContent(isLogin: Boolean) {
        setComposable {
            CafeRecommenderApp(
                userPreference = userPreference,
                isLogin = isLogin,
            )
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
                permissions[READ_MEDIA_IMAGES] ?: false -> {
                    // do nothing
                }
                else -> {
                    locationViewModel.setPermissionToNotGranted()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getUserLocation(permissions: Array<String>) {
        if (checkPermission(ACCESS_FINE_LOCATION) &&
            checkPermission(ACCESS_COARSE_LOCATION)
        ) {
            locationViewModel.getUserLocation(this)
        } else {
            requestForPermissionLauncher.launch(permissions)
        }
    }

    companion object {
        private const val REQUEST_APPLICATION_SETTINGS = 100
    }
}