package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeDetailInfoItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FacilitiesItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FavoriteButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProgressBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SectionBig
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.ErrorScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.lang.Float.min

@Composable
fun DetailScreen(
    userPreference: UserPreference,
    itemId: String,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = PreferenceViewModelFactory(userPreference)
    ),
    state: DetailState = rememberDetailState(
        isFavorite = false,
        showMapDialog = false,
        isGetLocationHandled = false,
    ),
) {
    val context = LocalContext.current
    val mapsState = viewModel.mapsUiState.collectAsState().value

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.serving_up_cafe_info))
                viewModel.getCafeById(itemId)
            }
            is UiState.Success -> {
                val cafe = uiState.data
                if (!state.isGetLocationHandled) {
                    viewModel.getLocationFromAddress(context, cafe.address)
                    state.isGetLocationHandled = true
                }
                DetailContent(
                    thumbnail = cafe.thumbnail,
                    name = cafe.name,
                    address = cafe.address,
                    description = cafe.description,
                    rating = cafe.rating,
                    review = cafe.review,
                    openingHour = cafe.openingHour,
                    closingHour = cafe.closingHour,
                    priceCategory = cafe.priceCategory,
                    region = cafe.region,
                    facilities = cafe.facilities,
                    toggleFavorite = { state.isFavorite = !state.isFavorite },
                    isFavorite = state.isFavorite,
                    navigateBack = navigateBack,
                    toggleShowMapDialog = { state.showMapDialog = !state.showMapDialog },
                    mapsState = mapsState,
                    getLocation = { viewModel.getLocationFromAddress(context, cafe.address) },
                )
            }
            is UiState.Error -> {
                ErrorScreen(
                    text = stringResource(id = R.string.error_loading_cafe_detail),
                    onRetry = {
                        viewModel.getCafeById(itemId)
                    }
                )
            }
        }
    }

    if (state.showMapDialog) {
        AlertDialog(
            confirmButton = {
                TextButton(onClick = { state.showMapDialog = false }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            onDismissRequest = { state.showMapDialog = false },
            title = {
                Text(text = stringResource(id = R.string.cannot_open_google_maps))
            },
            text = {
                Text(text = stringResource(id = R.string.google_maps_not_installed))
            },
        )
    }
}

class DetailState(
    initialIsFavoriteState: Boolean,
    initialShowMapDialogState: Boolean,
    initialIsGetLocationHandledState: Boolean,
) {
    var isFavorite by mutableStateOf(initialIsFavoriteState)
    var showMapDialog by mutableStateOf(initialShowMapDialogState)
    var isGetLocationHandled by mutableStateOf(initialIsGetLocationHandledState)
}

@Composable
fun rememberDetailState(
    isFavorite: Boolean,
    showMapDialog: Boolean,
    isGetLocationHandled: Boolean,
): DetailState =
    remember(isFavorite, showMapDialog, isGetLocationHandled) {
        DetailState(isFavorite, showMapDialog, isGetLocationHandled)
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    thumbnail: String,
    name: String,
    address: String,
    description: String,
    rating: Double,
    review: String,
    openingHour: Int,
    closingHour: Int,
    priceCategory: String,
    region: String,
    facilities: List<Pair<Facility, Boolean>>,
    toggleFavorite: () -> Unit,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    toggleShowMapDialog: () -> Unit,
    mapsState: UiState<LatLng>,
    getLocation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
    ) {
        AsyncImage(
            model = thumbnail,
            contentDescription = "Image of $name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value * 0.1f
                }
        )
        Column(
            modifier = Modifier.padding(APP_CONTENT_PADDING)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium,
                color = Gray,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Column (modifier.weight(1f)) {
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.location_border),
                        text = region
                    )
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.star_border),
                        text = "$rating/5.0 ($review)"
                    )
                }
                Column (modifier.weight(1f)) {
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.time),
                        text = "$openingHour - $closingHour"
                    )
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.price_category),
                        text = priceCategory
                    )
                }
            }
            Divider(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp))
            SectionBig(
                title = stringResource(id = R.string.description),
                content = {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray,
                    )
                }
            )
            SectionBig(
                title = stringResource(id = R.string.facilities),
                content = {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        facilities.forEach { (facility, value) ->
                            if (value) {
                                FacilitiesItem(text = facility.displayName)
                            }
                        }
                    }
                }
            )
            Divider(modifier = Modifier.padding(16.dp))
            SectionBig(
                title = stringResource(id = R.string.location),
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        mapsState.let { state ->
                            when (state) {
                                UiState.Loading -> {
                                    ProgressBar(Modifier.size(40.dp))
                                }
                                is UiState.Success -> {
                                    GoogleMap(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(240.dp),
                                        cameraPositionState = rememberCameraPositionState {
                                            position = CameraPosition.fromLatLngZoom(state.data, 15f)
                                        }
                                    ) {
                                        Marker(
                                            state = MarkerState(position = state.data),
                                            title = name,
                                            snippet = address
                                        )
                                    }
                                    Button(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = stringResource(id = R.string.view_on_google_maps),
                                    ) {
                                        navigateToGoogleMaps(context, state.data, toggleShowMapDialog)
                                    }
                                }
                                is UiState.Error -> {
                                    Text(stringResource(id = R.string.failed_to_get_location))
                                    ButtonSmall(text = stringResource(id = R.string.retry)) {
                                        if (state != UiState.Loading) getLocation()
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        FavoriteButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .zIndex(1f),
            onClick = toggleFavorite,
            isFavorite = isFavorite,
            showBackground = true,
        )
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .zIndex(1f),
            onClick = navigateBack,
            showBackground = true
        )
    }
}

fun navigateToGoogleMaps(context: Context, latLng: LatLng, toggleShowMapDialog: () -> Unit) {
    val uri = Uri.parse("geo:${latLng.latitude},${latLng.longitude}?q=${latLng.latitude},${latLng.longitude}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        toggleShowMapDialog()
    }
}
