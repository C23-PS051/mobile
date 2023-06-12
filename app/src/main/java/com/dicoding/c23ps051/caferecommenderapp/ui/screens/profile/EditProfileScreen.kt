package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.MIN_PASSWORD_LENGTH
import com.dicoding.c23ps051.caferecommenderapp.constants.NAME_REGEX
import com.dicoding.c23ps051.caferecommenderapp.constants.PASSWORD_REGEX
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ChangeProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.components.LabeledOutlinedFormTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordLabeledOutlinedTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PictureChooserDialog
import com.dicoding.c23ps051.caferecommenderapp.ui.components.RadioGroup
import com.dicoding.c23ps051.caferecommenderapp.ui.components.StandardTopBar
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.CameraPermissionScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.CameraView
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera.IntentGalleryLauncher
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.info.InfoScreen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.STARTER_CONTENT_PADDING
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var cameraExecutor: ExecutorService

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditProfileScreen(
    navigateUp: () -> Unit,
    state: EditProfileState = rememberEditProfileState(
        showPictureChooserDialog = false,
        photoUrl = "", /* TODO: REMEMBER TO CHANGE TO VALUE FROM VIEW MODEL */
        radio = listOf(true, false), /* TODO: REMEMBER TO CHANGE TO VALUE FROM VIEW MODEL */
        enabledPassword = false,
        nameText = "", /* TODO: REMEMBER TO CHANGE TO VALUE FROM VIEW MODEL */
        passwordText = "",
        repasswordText = "",
        nameHasError = false,
        passwordHasError = false,
        repasswordHasError = false,
        showPassword = false,
        showRepassword = false,
        checkboxState = false,
    ),
) {
    cameraExecutor = Executors.newSingleThreadExecutor()
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    var shouldShowCamera by remember { mutableStateOf(false) }
    var cameraSettings by remember { mutableStateOf(false) }
    val hasCameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
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
                    cameraSelector =
                        if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                        else CameraSelector.DEFAULT_BACK_CAMERA
                },
                onError = { Log.e("MyLogger", "Error: $it") },
                navigateUp = {
                    showCameraView = false
                }
            )
        }

        else -> {
            EditProfileContent(
                nameText = state.nameText,
                passwordText = state.passwordText,
                repasswordText = state.repasswordText,
                photoUrl = state.photoUrl,
                onAddProfilePicture = {
                    if (hasCameraPermission.status == PermissionStatus.Granted) {
                        state.showPictureChooserDialog = true
                    } else {
                        requestCameraPermissionState = true
                    }
                },
                navigateUp = navigateUp,
                radioState = state.radioState,
                enabledPasswordState = state.enabledPassword,
                onRadioButtonClick = { i ->
                    var index = 0
                    while (index < state.radioState.size) {
                        state.radioState[index] = index == i
                        index++
                    }
                },
                onNameValueChange = { newText ->
                    state.nameText = newText
                    val nameRegex = Regex(NAME_REGEX)
                    state.nameHasError = !nameRegex.matches(state.nameText)

                },
                onPasswordValueChange = { newText: String ->
                    state.passwordText = newText
                    val passwordRegex = Regex(PASSWORD_REGEX)
                    state.passwordHasError =
                        state.passwordText.length < MIN_PASSWORD_LENGTH || !passwordRegex.matches(
                            state.passwordText
                        )
                    state.repasswordHasError = state.repasswordText != state.passwordText
                },
                onRepasswordValueChange = { newText ->
                    state.repasswordText = newText
                    state.repasswordHasError = state.repasswordText != state.passwordText
                },
                showPassword = state.showPassword,
                showRepassword = state.showRepassword,
                nameHasError = state.nameHasError,
                passwordHasError = state.passwordHasError,
                repasswordHasError = state.repasswordHasError,
                onPasswordTrailingIconClick = { state.showPassword = !state.showPassword },
                onRepasswordTrailingIconClick = { state.showRepassword = !state.showRepassword },
                checkboxState = state.checkboxState,
                toggleCheckboxState = {
                    state.checkboxState = !state.checkboxState
                    state.enabledPassword = !state.enabledPassword
                }
            )
        }
    }
}

class EditProfileState(
    initialShowPictureDialogState: Boolean,
    initialPhotoUrlState: String,
    initialRadioState: List<Boolean>,
    initialEnabledPasswordState: Boolean,
    initialNameTextState: String,
    initialPasswordTextState: String,
    initialRepasswordTextState: String,
    initialNameHasErrorState: Boolean,
    initialPasswordHasErrorState: Boolean,
    initialRepasswordHasErrorState: Boolean,
    initialShowPasswordState: Boolean,
    initialShowRepasswordState: Boolean,
    initialCheckboxState: Boolean,
) {
    var showPictureChooserDialog by mutableStateOf(initialShowPictureDialogState)
    var photoUrl by mutableStateOf(initialPhotoUrlState)
    var radioState = mutableStateListOf<Boolean>().apply { addAll(initialRadioState) }
    var enabledPassword by mutableStateOf(initialEnabledPasswordState)
    var nameText by mutableStateOf(initialNameTextState)
    var passwordText by mutableStateOf(initialPasswordTextState)
    var repasswordText by mutableStateOf(initialRepasswordTextState)
    var nameHasError by mutableStateOf(initialNameHasErrorState)
    var passwordHasError by mutableStateOf(initialPasswordHasErrorState)
    var repasswordHasError by mutableStateOf(initialRepasswordHasErrorState)
    var showPassword by mutableStateOf(initialShowPasswordState)
    var showRepassword by mutableStateOf(initialShowRepasswordState)
    var checkboxState by mutableStateOf(initialCheckboxState)
}

@Composable
fun rememberEditProfileState(
    showPictureChooserDialog: Boolean,
    photoUrl: String,
    radio: List<Boolean>,
    enabledPassword: Boolean,
    nameHasError: Boolean,
    nameText: String,
    passwordText: String,
    repasswordText: String,
    passwordHasError: Boolean,
    repasswordHasError: Boolean,
    showPassword: Boolean,
    showRepassword: Boolean,
    checkboxState: Boolean,
) : EditProfileState = remember(
    showPictureChooserDialog, photoUrl, radio, enabledPassword, nameText, passwordText, repasswordText,
        nameHasError, passwordHasError, repasswordHasError, showPassword, showRepassword, checkboxState
) {
    EditProfileState(showPictureChooserDialog, photoUrl, radio, enabledPassword, nameText, passwordText, repasswordText,
        nameHasError, passwordHasError, repasswordHasError, showPassword, showRepassword, checkboxState)
}

@Composable
fun EditProfileContent(
    nameText: String,
    passwordText: String,
    repasswordText: String,
    photoUrl: String,
    onAddProfilePicture: () -> Unit,
    navigateUp: () -> Unit,
    radioState: List<Boolean>,
    enabledPasswordState: Boolean,
    onRadioButtonClick: (Int) -> Unit,
    onNameValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onRepasswordValueChange: (String) -> Unit,
    showPassword: Boolean,
    showRepassword: Boolean,
    nameHasError: Boolean,
    passwordHasError: Boolean,
    repasswordHasError: Boolean,
    onPasswordTrailingIconClick: () -> Unit,
    onRepasswordTrailingIconClick: () -> Unit,
    checkboxState: Boolean,
    toggleCheckboxState: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopBar(
                title = stringResource(id = R.string.edit_profile),
                showBackButton = true,
                onBack = navigateUp,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = STARTER_CONTENT_PADDING)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ChangeProfilePicture(
                imageUrl = photoUrl,
                onTextClick = onAddProfilePicture,
                imageSize = 144,
            )
            Spacer(modifier = Modifier.height(24.dp))
            EditProfileForm(
                nameText = nameText,
                passwordText = passwordText,
                repasswordText = repasswordText,
                radioState = radioState,
                enabledPasswordState = enabledPasswordState,
                onRadioButtonClick = onRadioButtonClick,
                onNameValueChange = onNameValueChange,
                onPasswordValueChange = onPasswordValueChange,
                onRepasswordValueChange = onRepasswordValueChange,
                showPassword = showPassword,
                showRepassword = showRepassword,
                nameHasError = nameHasError,
                passwordHasError = passwordHasError,
                repasswordHasError = repasswordHasError,
                onPasswordTrailingIconClick = onPasswordTrailingIconClick,
                onRepasswordTrailingIconClick = onRepasswordTrailingIconClick,
                checkboxState = checkboxState,
                toggleCheckboxState = toggleCheckboxState
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(text = stringResource(id = R.string.save_changes)) {
                /*TODO*/
            }
        }
    }
}

@Composable
fun EditProfileForm(
    nameText: String,
    passwordText: String,
    repasswordText: String,
    radioState: List<Boolean>,
    enabledPasswordState: Boolean,
    onRadioButtonClick: (Int) -> Unit,
    onNameValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onRepasswordValueChange: (String) -> Unit,
    showPassword: Boolean,
    showRepassword: Boolean,
    nameHasError: Boolean,
    passwordHasError: Boolean,
    repasswordHasError: Boolean,
    onPasswordTrailingIconClick: () -> Unit,
    onRepasswordTrailingIconClick: () -> Unit,
    checkboxState: Boolean,
    toggleCheckboxState: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LabeledOutlinedFormTextField(
            label = stringResource(id = R.string.name),
            text = nameText,
            hasError = nameHasError,
            onValueChange = onNameValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = if (enabledPasswordState) ImeAction.Next else ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down,
                    )
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .clickable { toggleCheckboxState(checkboxState) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checkboxState, onCheckedChange = toggleCheckboxState)
            Text(
                stringResource(id = R.string.i_want_to_change_password),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        PasswordLabeledOutlinedTextField(
            enabled = enabledPasswordState,
            label = stringResource(id = R.string.password),
            text = passwordText,
            hasError = passwordHasError,
            onValueChange = onPasswordValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down,
                    )
                },
            ),
            onTrailingIconClick = onPasswordTrailingIconClick,
            showPassword = showPassword,
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordLabeledOutlinedTextField(
            enabled = enabledPasswordState,
            label = stringResource(id = R.string.repassword),
            text = repasswordText,
            hasError = repasswordHasError,
            onValueChange = onRepasswordValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            onTrailingIconClick = onRepasswordTrailingIconClick,
            showPassword = showRepassword,
        )
        Spacer(modifier = Modifier.height(16.dp))
        RadioGroup(
            selected = radioState,
            items = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female)),
            onClick = onRadioButtonClick,
        )
    }
}
