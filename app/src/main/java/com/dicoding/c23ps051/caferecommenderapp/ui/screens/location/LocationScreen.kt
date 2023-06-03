package com.dicoding.c23ps051.caferecommenderapp.ui.screens.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button

@Composable
fun LocationScreen(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

//    viewModel.uiState.collectAsState().value.let { uiState ->
//        when (uiState) {
//            is UiState.Loading -> {
//                // do nothing
//            }
//            is UiState.Success -> {
//                navigateToHome()
//            }
//            is UiState.Error -> {
//                /*TODO*/
//            }
//        }
//    }

//    val requestForPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        when {
//            permissions[ACCESS_FINE_LOCATION] == true -> {
//                getUserLocation(context)
//            }
//            permissions[ACCESS_COARSE_LOCATION] == true -> {
//                getUserLocation(context)
//            }
//            else -> {
//                // Handle permission denial
//            }
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
        )
        Text(
            text = stringResource(R.string.need_location_permission),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 24.dp)
        )
        Button(text = stringResource(id = R.string.turn_on_location)) {
            onButtonClick()
        }
    }
}
