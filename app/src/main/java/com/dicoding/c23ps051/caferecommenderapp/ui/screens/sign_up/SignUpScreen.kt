package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.MIN_PASSWORD_LENGTH
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ClickableText
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProfilePicture
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.STARTER_CONTENT_PADDING

@Composable
fun SignUpScreen(
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit,
    nameText: String = "",
    emailText: String = "",
    photoUrl: String = "",
    state: SignUpFormState = rememberSignUpFormState(
        photoUrl = photoUrl,
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
            val nameRegex = Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9\\s]+\$")
            state.nameHasError = !nameRegex.matches(state.nameText)

        },
        onEmailValueChange = { newText: String ->
            state.emailText = newText
            val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
            state.emailHasError = !emailRegex.matches(state.emailText)
        },
        onPasswordValueChange = { newText: String ->
            state.passwordText = newText
            val passwordRegex = Regex("^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$")
            state.passwordHasError =
                state.passwordText.length < MIN_PASSWORD_LENGTH || !passwordRegex.matches(state.passwordText)
        },
        onPasswordTrailingIconClick = { state.showPassword = !state.showPassword },
        onRadioButtonClick = { i ->
            var index = 0
            while (index < state.radioState.size) {
                state.radioState[index] = index == i
                index++
            }
        }
    )
}

class SignUpFormState(
    initialPhotoUrlState: String,
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
    nameText: String,
    nameHasError: Boolean,
    emailText: String,
    emailHasError: Boolean,
    passwordText: String,
    passwordHasError: Boolean,
    showPassword: Boolean,
    radio: List<Boolean>,
): SignUpFormState = remember(
    photoUrl, nameText, nameHasError, emailText, emailHasError, passwordText, passwordHasError, showPassword, radio
) {
    SignUpFormState(photoUrl, nameText, nameHasError, emailText, emailHasError, passwordText, passwordHasError, showPassword, radio)
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
            ProfilePicture(
                image = photoUrl,
                modifier = Modifier.size(112.dp)
            )
            ClickableText(
                stringResource(id = R.string.add_profile_picture),
                onClick = {
                    /*TODO*/
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
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