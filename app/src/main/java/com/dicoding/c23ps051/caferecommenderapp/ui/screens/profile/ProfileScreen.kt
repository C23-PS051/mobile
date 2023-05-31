package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.AuthViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.StandardTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SettingsItem
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    userPreference: UserPreference,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    navigateToApplicationInfo: () -> Unit,
    preferenceViewModel: PreferenceViewModel = viewModel(factory = PreferenceViewModelFactory(userPreference)),
    authViewModel: AuthViewModel = viewModel()
) {
    val googleSignInClient: GoogleSignInClient
    val context = LocalContext.current

    googleSignInClient = authViewModel.initGoogleSignInClient(context)

    preferenceViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                preferenceViewModel.getLogin()
            }
            is UiState.Success -> {
                val data = uiState.data
                ProfileContent(
                    name = data.name,
                    email = data.email,
                    photoUrl = data.photoUrl,
                    onLogoutClick = {
                        googleSignInClient.signOut().addOnCompleteListener {
                            googleSignInClient.revokeAccess().addOnCompleteListener {
                                preferenceViewModel.logout()
                            }
                        }
                    },
                    onPrivacyPolicyClick = navigateToPrivacyPolicy,
                    onHelpCenterClick = navigateToHelpCenter,
                    onApplicationInfoClick = navigateToApplicationInfo,
                )
            }
            is UiState.Error -> {
                /*TODO*/
            }
        }
    }
}

@Composable
fun ProfileContent(
    name: String,
    email: String,
    photoUrl: String,
    onLogoutClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onHelpCenterClick: () -> Unit,
    onApplicationInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(innerPadding)
                .padding(APP_CONTENT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture(image = photoUrl)
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = email
            )
            ButtonSmall(text = stringResource(id = R.string.edit_profile)) {
                /* TODO */
            }
            Divider(modifier = Modifier.padding(vertical = 24.dp))
            SettingsItem(
                text = stringResource(id = R.string.privacy_policy),
                starterIcon = painterResource(id = R.drawable.lock),
                onClick = onPrivacyPolicyClick
            )
            SettingsItem(
                text = stringResource(id = R.string.help_center),
                starterIcon = painterResource(id = R.drawable.help),
                onClick = onHelpCenterClick
            )
            SettingsItem(
                text = stringResource(id = R.string.application_info),
                starterIcon = painterResource(id = R.drawable.info),
                onClick = onApplicationInfoClick
            )
            Divider(modifier = Modifier.padding(vertical = 20.dp))
            SettingsItem(
                text = stringResource(id = R.string.logout),
                starterIcon = painterResource(id = R.drawable.logout),
                onClick = onLogoutClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

}