package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SettingsItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.StandardTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.loading.LoadingScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun ProfileScreen(
    userPreference: UserPreference,
    navigateToEditProfile: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    navigateToApplicationInfo: () -> Unit,
    preferenceViewModel: PreferenceViewModel = viewModel(factory = PreferenceViewModelFactory(userPreference)),
    authViewModel: AuthViewModel = viewModel(),
    state: ProfileState = rememberProfileState(showDialog = false)
) {
    val googleSignInClient: GoogleSignInClient
    val context = LocalContext.current

    googleSignInClient = authViewModel.initGoogleSignInClient(context)

    preferenceViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen(stringResource(id = R.string.fetching_your_data))
                preferenceViewModel.getLogin()
            }
            is UiState.Success -> {
                val data = uiState.data
                ProfileContent(
                    name = data.name,
                    email = data.email,
                    photoUrl = data.photoUrl,
                    onLogoutClick = {
                        state.showDialog = true
                    },
                    buttonText = stringResource(id = R.string.edit_profile),
                    onButtonClick = navigateToEditProfile,
                    onPrivacyPolicyClick = navigateToPrivacyPolicy,
                    onHelpCenterClick = navigateToHelpCenter,
                    onApplicationInfoClick = navigateToApplicationInfo,
                )
            }
            is UiState.Error -> {
                ProfileContent(
                    name = stringResource(id = R.string.cannot_load_name),
                    email = stringResource(id = R.string.cannot_load_email),
                    photoUrl = "",
                    onLogoutClick = {
                        state.showDialog = true
                    },
                    buttonText = stringResource(id = R.string.retry),
                    onButtonClick = {
                        preferenceViewModel.getLogin()
                    },
                    onPrivacyPolicyClick = navigateToPrivacyPolicy,
                    onHelpCenterClick = navigateToHelpCenter,
                    onApplicationInfoClick = navigateToApplicationInfo,
                )
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            confirmButton = {
                TextButton(onClick = {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        val currentUser = FirebaseAuth.getInstance().currentUser as FirebaseUser
                        if (currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }) {
                            googleSignInClient.signOut().addOnCompleteListener {
                                googleSignInClient.revokeAccess().addOnCompleteListener {
                                    preferenceViewModel.logout()
                                }
                            }
                        } else {
                            FirebaseAuth.getInstance().signOut()
                            preferenceViewModel.logout()
                        }
                    }
                    state.showDialog = false
                }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    state.showDialog = false
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            onDismissRequest = { state.showDialog = false },
            title = {
                Text(text = stringResource(id = R.string.logout_confirm_title))
            },
            text = {
                Text(text = stringResource(id = R.string.logout_confirm_text))
            },
        )
    }
}

class ProfileState(initialShowDialog: Boolean) {
    var showDialog by mutableStateOf(initialShowDialog)
}

@Composable
fun rememberProfileState(showDialog: Boolean): ProfileState =
    remember(showDialog) {
        ProfileState(showDialog)
    }

@Composable
fun ProfileContent(
    name: String,
    email: String,
    photoUrl: String,
    onLogoutClick: () -> Unit,
    buttonText: String,
    onButtonClick: () -> Unit,
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
            ButtonSmall(text = buttonText) {
                onButtonClick()
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