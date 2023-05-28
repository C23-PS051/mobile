package com.dicoding.c23ps051.caferecommenderapp.ui.screen.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.StandardTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun ProfileScreen(
    userPreference: UserPreference,
    modifier: Modifier = Modifier,
    preferenceViewModel: PreferenceViewModel = viewModel(factory = ViewModelFactory(userPreference)),
) {
    Scaffold(
        topBar = {
            StandardTopBar(
                title = stringResource(id = R.string.your_profile)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(APP_CONTENT_PADDING)
        ) {
            /*TODO: TO BE UPDATED*/
            Button(text = "Logout") {
                preferenceViewModel.logout()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

}