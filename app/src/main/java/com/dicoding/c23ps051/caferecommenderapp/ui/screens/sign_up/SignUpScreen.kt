package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import android.Manifest.permission.CAMERA
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.EMAIL_REGEX
import com.dicoding.c23ps051.caferecommenderapp.constants.MIN_PASSWORD_LENGTH
import com.dicoding.c23ps051.caferecommenderapp.constants.NAME_REGEX
import com.dicoding.c23ps051.caferecommenderapp.constants.PASSWORD_REGEX
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ChangeProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ClickableText
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PictureChooserDialog
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.CameraPermissionScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.CameraView
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.IntentGalleryLauncher
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.InfoScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.STARTER_CONTENT_PADDING
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var cameraExecutor: ExecutorService

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignUpScreen(
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit,
    nameText: String = "",
    emailText: String = "",
    photoUrl: String = "",
    state: SignUpFormState = rememberSignUpFormState(
        photoUrl = photoUrl,
        showPictureChooserDialog = false,
        nameText = nameText,
        nameHasError = false,
        emailText = emailText,
        emailHasError = false,
        passwordText = "",
        passwordHasError = false,
        showPassword = false,
        radio = listOf(true, false),
    ),
) {
    cameraExecutor = Executors.newSingleThreadExecutor()
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    var shouldShowCamera by remember { mutableStateOf(false) }
    var cameraSettings by remember { mutableStateOf(false) }
    val hasCameraPermission = rememberPermissionState(CAMERA)
    var showInfoScreen by remember { mutableStateOf(false) }
    var requestCameraPermissionState by remember { mutableStateOf(false) }
    var launchIntentGallery by remember { mutableStateOf(false) }
    var showCameraView by remember { mutableStateOf(false) }

    if (state.showPictureChooserDialog) {
        Dialog(
            onDismissRequest = { state.showPictureChooserDialog = false },
            content = {
                PictureChooserDialog(
                    onTakePicture = {
                        state.showPictureChooserDialog = false
                        showCameraView = true
                    },
                    onChooseFromGallery = {
                        state.showPictureChooserDialog = false
                        launchIntentGallery = true
                    },
                    onCancel = {
                        state.showPictureChooserDialog = false
                    }
                )
            }
        )
    }

    // When condition for launchers
    when {
        requestCameraPermissionState -> {
            CameraPermissionScreen(
                navigateBack = {
                    requestCameraPermissionState = false
                    cameraSettings = false
                },
                showInfoScreen = { condition ->
                    showInfoScreen = condition
                },
                cameraSettings = cameraSettings
            )
        }
        launchIntentGallery -> {
            IntentGalleryLauncher(
                postImage = { uri ->
                    state.photoUrl = uri.toString()
                    launchIntentGallery = false
                },
                navigateBack = { launchIntentGallery = false }
            )
        }
    }

    // When condition for screens
    when {
        showInfoScreen -> {
            InfoScreen(
                text = stringResource(R.string.camera_permission_not_granted),
                actionText = stringResource(R.string.go_to_settings),
                secondaryActionText = stringResource(R.string.skip),
                action = {
                    cameraSettings = true
                },
                secondaryAction = {
                    showInfoScreen = false
                    requestCameraPermissionState = false
                }
            )
        }
        showCameraView -> {
            CameraView(
                cameraSelector = cameraSelector,
                executor = cameraExecutor,
                onImageCaptured = { uri ->
                    state.photoUrl = uri.toString()
                    shouldShowCamera = false
                    showCameraView = false
                },
                onSwitchCamera = {
                    cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                },
                onError = { Log.e("MyLogger", "Error: $it") },
                navigateUp = {
                    showCameraView = false
                }
            )
        }
        else -> {
            SignUpContent(
                photoUrl = state.photoUrl,
                nameText = state.nameText,
                nameHasError = state.nameHasError,
                emailText = state.emailText,
                emailHasError = state.emailHasError,
                passwordText = state.passwordText,
                passwordHasError = state.passwordHasError,
                showPassword = state.showPassword,
                radioState = state.radioState,
                navigateUp = navigateUp,
                navigateToSignIn = navigateToSignIn,
                onNameValueChange = { newText ->
                    state.nameText = newText
                    val nameRegex = Regex(NAME_REGEX)
                    state.nameHasError = !nameRegex.matches(state.nameText)

                },
                onEmailValueChange = { newText: String ->
                    state.emailText = newText
                    val emailRegex =
                        Regex(EMAIL_REGEX)
                    state.emailHasError = !emailRegex.matches(state.emailText)
                },
                onPasswordValueChange = { newText: String ->
                    state.passwordText = newText
                    val passwordRegex = Regex(PASSWORD_REGEX)
                    state.passwordHasError =
                        state.passwordText.length < MIN_PASSWORD_LENGTH || !passwordRegex.matches(
                            state.passwordText
                        )
                },
                onPasswordTrailingIconClick = { state.showPassword = !state.showPassword },
                onRadioButtonClick = { i ->
                    var index = 0
                    while (index < state.radioState.size) {
                        state.radioState[index] = index == i
                        index++
                    }
                },
                onAddProfilePicture = {
                    if (hasCameraPermission.status == PermissionStatus.Granted) {
                        state.showPictureChooserDialog = true
                    } else {
                        requestCameraPermissionState = true
                    }
                }
            )
        }
    }
}

class SignUpFormState(
    initialPhotoUrlState: String,
    initialPictureChooserState: Boolean,
    initialNameTextState: String,
    initialNameHasErrorState: Boolean,
    initialEmailTextState: String,
    initialEmailHasErrorState: Boolean,
    initialPasswordTextState: String,
    initialPasswordHasErrorState: Boolean,
    initialShowPasswordState: Boolean,
    initialRadioState: List<Boolean>,
) {
    /* Profile Picture Url State */
    var photoUrl by mutableStateOf(initialPhotoUrlState)
    var showPictureChooserDialog by mutableStateOf(initialPictureChooserState)

    /* Name Field State */
    var nameText by mutableStateOf(initialNameTextState)
    var nameHasError by mutableStateOf(initialNameHasErrorState)

    /* Email Field State */
    var emailText by mutableStateOf(initialEmailTextState)
    var emailHasError by mutableStateOf(initialEmailHasErrorState)

    /* Password Field State */
    var passwordText by mutableStateOf(initialPasswordTextState)
    var passwordHasError by mutableStateOf(initialPasswordHasErrorState)
    var showPassword by mutableStateOf(initialShowPasswordState)

    /* Radio Button State */
    var radioState = mutableStateListOf<Boolean>().apply { addAll(initialRadioState) }
}

@Composable
fun rememberSignUpFormState(
    photoUrl: String,
    showPictureChooserDialog: Boolean,
    nameText: String,
    nameHasError: Boolean,
    emailText: String,
    emailHasError: Boolean,
    passwordText: String,
    passwordHasError: Boolean,
    showPassword: Boolean,
    radio: List<Boolean>,
): SignUpFormState = remember(
    photoUrl, showPictureChooserDialog, nameText, nameHasError, emailText, emailHasError, passwordText, passwordHasError, showPassword, radio
) {
    SignUpFormState(photoUrl, showPictureChooserDialog, nameText, nameHasError, emailText, emailHasError, passwordText, passwordHasError, showPassword, radio)
}

@Composable
fun SignUpContent(
    photoUrl: String,
    nameText: String,
    nameHasError: Boolean,
    onNameValueChange: (String) -> Unit,
    emailText: String,
    emailHasError: Boolean,
    onEmailValueChange: (String) -> Unit,
    passwordText: String,
    passwordHasError: Boolean,
    showPassword: Boolean,
    onPasswordValueChange: (String) -> Unit,
    onPasswordTrailingIconClick: () -> Unit,
    radioState: List<Boolean>,
    onRadioButtonClick: (Int) -> Unit,
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit,
    onAddProfilePicture: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        BackButton(
            onClick = { navigateUp() },
            modifier = Modifier.padding(APP_CONTENT_PADDING)
        )
        Column(
            modifier = modifier
                .padding(horizontal = STARTER_CONTENT_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChangeProfilePicture(
                imageUrl = photoUrl,
                onTextClick = onAddProfilePicture,
            )
            Spacer(modifier = Modifier.height(36.dp))
            SignUpForm(
                nameText = nameText,
                nameHasError = nameHasError,
                onNameValueChange = onNameValueChange,
                emailText = emailText,
                emailHasError = emailHasError,
                onEmailValueChange = onEmailValueChange,
                passwordText = passwordText,
                passwordHasError = passwordHasError,
                onPasswordValueChange = onPasswordValueChange,
                onPasswordTrailingIconClick = onPasswordTrailingIconClick,
                showPassword = showPassword,
                radioState = radioState,
                onRadioButtonClick = onRadioButtonClick,
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(text = stringResource(id = R.string.sign_up)) {
                /* TODO */
            }
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = stringResource(id = R.string.already_have_account),
                onClick = {
                    navigateToSignIn()
                }
            )
        }
    }
}