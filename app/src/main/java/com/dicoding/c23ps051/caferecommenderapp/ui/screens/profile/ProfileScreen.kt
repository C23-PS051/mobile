package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.dicoding.c23ps051.caferecommenderapp.constants.DEFAULT_PHOTO_URI
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.AuthViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.ViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ButtonSmall
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProgressBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SettingsItem
import com.dicoding.c23ps051.caferecommenderapp.ui.components.StandardTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
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
    profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(userPreference)),
    authViewModel: AuthViewModel = viewModel(),
    state: ProfileState = rememberProfileState(
        showDialog = false,
        loadingState = true,
        userName = "",
        userEmail = "",
        photoUri = "",
        buttonAction = {},
        buttonText = "",
    )
) {
    val googleSignInClient: GoogleSignInClient
    val context = LocalContext.current

    googleSignInClient = authViewModel.initGoogleSignInClient(context)

    ProfileContent(
        loadingState = state.loadingState,
        name = state.userName,
        email = state.userEmail,
        photoUri = state.photoUri,
        onLogoutClick = {
            state.showDialog = true
        },
        buttonText = state.buttonText,
        onButtonClick = state.buttonAction,
        onPrivacyPolicyClick = navigateToPrivacyPolicy,
        onHelpCenterClick = navigateToHelpCenter,
        onApplicationInfoClick = navigateToApplicationInfo,
    )

    profileViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                state.loadingState = true
                profileViewModel.getUserProfile()
            }
            is UiState.Success -> {
                state.loadingState = false
                val data = uiState.data
                Log.d("MyLogger", data.photoUri)
                state.userName = data.fullName
                state.userEmail = data.email
                state.photoUri = data.photoUri
                state.buttonAction = navigateToEditProfile
                state.buttonText = stringResource(id = R.string.edit_profile)
            }
            is UiState.Error -> {
                state.loadingState = false
                state.userName = stringResource(id = R.string.cannot_load_name)
                state.userEmail = stringResource(id = R.string.cannot_load_email)
                state.photoUri = DEFAULT_PHOTO_URI
                state.buttonAction = {
                    profileViewModel.getUserProfile()
                }
                state.buttonText = stringResource(id = R.string.retry)
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
                                    FirebaseAuth.getInstance().signOut()
                                    profileViewModel.logout()
                                }
                            }
                        } else {
                            FirebaseAuth.getInstance().signOut()
                            profileViewModel.logout()
                        }
                    }

//                    googleSignInClient.signOut().addOnCompleteListener {
//                        googleSignInClient.revokeAccess().addOnCompleteListener {
//                            FirebaseAuth.getInstance().signOut()
//                            profileViewModel.logout()
//                        }
//                    }
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

class ProfileState(
    initialShowDialog: Boolean,
    initialLoadingState: Boolean,
    initialUserNameState: String,
    initialUserEmailState: String,
    initialPhotoUriState: String,
    initialButtonActionState: () -> Unit,
    initialButtonTextState: String,
) {
    var showDialog by mutableStateOf(initialShowDialog)
    var loadingState by mutableStateOf(initialLoadingState)
    var userName by mutableStateOf(initialUserNameState)
    var userEmail by mutableStateOf(initialUserEmailState)
    var photoUri by mutableStateOf(initialPhotoUriState)
    var buttonAction by mutableStateOf(initialButtonActionState)
    var buttonText by mutableStateOf(initialButtonTextState)
}

@Composable
fun rememberProfileState(
    showDialog: Boolean,
    loadingState: Boolean,
    userName: String,
    userEmail: String,
    photoUri: String,
    buttonAction: () -> Unit,
    buttonText: String
): ProfileState =
    remember(showDialog, loadingState, userName, userEmail, photoUri, buttonAction, buttonText) {
        ProfileState(showDialog, loadingState, userName, userEmail, photoUri, buttonAction, buttonText)
    }

@Composable
fun ProfileContent(
    loadingState: Boolean,
    name: String,
    email: String,
    photoUri: String,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(272.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loadingState) {
                    ProgressBar()
                } else {
                    ProfilePicture(image = photoUri)
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
                }
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