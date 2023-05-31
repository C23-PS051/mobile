package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.di.Injection
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.RepositoryViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.CafeDetailInfoItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.FavoriteButton
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING

@Composable
fun DetailScreen(
    itemId: Long,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = RepositoryViewModelFactory(
            Injection.provideRepository()
        )
    )
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
                    ratingCount = cafe.ratingCount,
                    distance = cafe.distance,
                    isOpen = cafe.isOpen,
                    minPrice = cafe.minPrice,
                    maxPrice = cafe.maxPrice,
                    toggleFavorite = { /*TODO*/ },
                    isFavorite = false,
                    navigateBack = navigateBack
                )
            }
            is UiState.Error -> {
                /* TODO */
            }
        }
    }

}

@Composable
fun DetailContent(
    thumbnail: String,
    name: String,
    address: String,
    rating: Double,
    ratingCount: Int,
    distance: Double,
    isOpen: Boolean,
    minPrice: Long,
    maxPrice: Long,
    toggleFavorite: () -> Unit,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
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
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Column (modifier.weight(1f)) {
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.location),
                        text = "$distance km"
                    )
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.star),
                        text = "$rating/5 ($ratingCount)"
                    )
                }
                Column (modifier.weight(1f)) {
                    CafeDetailInfoItem(
                        icon = painterResource(id = R.drawable.time),
                        text = "$rating" /* TODO: CHANGE TO TIME */
                    )
                    CafeDetailInfoItem(
                        icon = "Rp",
                        text = "$minPrice-$maxPrice"
                    )
                }
            }
            Divider(modifier = Modifier.padding(24.dp))
            Text(stringResource(id = R.string.description), style = MaterialTheme.typography.titleSmall)
            Text(address + address + address) /* TODO: CHANGE TO DESCRIPTION */
            Divider(modifier = Modifier.padding(24.dp))
            Text(stringResource(id = R.string.location), style = MaterialTheme.typography.titleSmall)
            /* TODO: ADD GOOGLE MAPS */
        }
    }
}