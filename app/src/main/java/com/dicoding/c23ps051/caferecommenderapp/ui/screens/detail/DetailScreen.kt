package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeDetailInfoItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FacilitiesItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FavoriteButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SectionBig
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.ErrorScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.google.maps.android.compose.GoogleMap

@Composable
fun DetailScreen(
    userPreference: UserPreference,
    itemId: String,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = PreferenceViewModelFactory(userPreference)
    ),
    state: DetailState = rememberDetailState(isFavorite = false),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.serving_up_cafe_info))
                viewModel.getCafeById(itemId)
            }
            is UiState.Success -> {
                val cafe = uiState.data
                DetailContent(
                    thumbnail = cafe.thumbnail,
                    name = cafe.name,
                    address = cafe.address,
                    rating = cafe.rating,
                    review = cafe.review,
                    openingHour = cafe.openingHour,
                    closingHour = cafe.closingHour,
                    priceCategory = cafe.priceCategory,
                    region = cafe.region,
                    facilities = cafe.facilities,
                    toggleFavorite = { state.isFavorite = !state.isFavorite },
                    isFavorite = state.isFavorite,
                    navigateBack = navigateBack
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
}

class DetailState(initialIsFavorite: Boolean) {
    var isFavorite by mutableStateOf(initialIsFavorite)
}

@Composable
fun rememberDetailState(isFavorite: Boolean): DetailState =
    remember(isFavorite) {
        DetailState(isFavorite)
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    thumbnail: String,
    name: String,
    address: String,
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
    modifier: Modifier = Modifier,
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
        ) {
            AsyncImage(
                model = thumbnail,
                contentDescription = "Image of $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            FavoriteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                onClick = toggleFavorite,
                isFavorite = isFavorite,
                showBackground = true,
            )
            BackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                onClick = navigateBack,
                showBackground = true
            )
        }
        Column(
            modifier = Modifier
                .padding(APP_CONTENT_PADDING),
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
                        text = "$openingHour - $closingHour" /* TODO: CHANGE TO TIME */
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
                        text = address + address + address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray,
                    ) /* TODO: CHANGE TO DESCRIPTION */
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
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }
            )
            Button(text = stringResource(id = R.string.view_on_google_maps)) {
                /* TODO: INTENT TO GOOGLE MAPS */
            }
        }
    }
}