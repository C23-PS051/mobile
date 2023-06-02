package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.dicoding.c23ps051.caferecommenderapp.R

class SignUpFormState(
    initialTextState: String,
    initialHasErrorState: Boolean,
    initialShowPasswordState: Boolean,
) {
    /* Name Field State */
    var nameText by mutableStateOf(initialTextState)
    var nameHasError by mutableStateOf(initialHasErrorState)

    /* Email Field State */
    var emailText by mutableStateOf(initialTextState)
    var emailHasError by mutableStateOf(initialHasErrorState)

    /* Password Field State */
    var passwordText by mutableStateOf(initialTextState)
    var passwordHasError by mutableStateOf(initialHasErrorState)
    var showPassword by mutableStateOf(initialShowPasswordState)

    /* Confirm Password Field State */
    var repasswordText by mutableStateOf(initialTextState)
    var repasswordHasError by mutableStateOf(initialHasErrorState)
    var showRepassword by mutableStateOf(initialShowPasswordState)
}

val signUpFormSaver = Saver<SignUpFormState, Bundle>(
    save = {
        bundleOf(
            "nameText" to it.nameText,
            "nameHasError" to it.nameHasError,
            "emailText" to it.emailText,
            "emailHasError" to it.emailHasError,
            "passwordText" to it.passwordText,
            "passwordHasError" to it.passwordHasError,
            "showPassword" to it.showPassword,
            "repasswordText" to it.repasswordText,
            "repasswordHasError" to it.repasswordHasError,
            "showRepassword" to it.showRepassword
        )
    },
    restore = {
        SignUpFormState(
            initialTextState = it.getString("text", ""),
            initialHasErrorState = it.getBoolean("hasError", false),
            initialShowPasswordState = it.getBoolean("showPassword", false)
        ).apply {
            nameText = it.getString("nameText", "")
            nameHasError = it.getBoolean("nameHasError", false)
            emailText = it.getString("emailText", "")
            emailHasError = it.getBoolean("emailHasError", false)
            passwordText = it.getString("passwordText", "")
            passwordHasError = it.getBoolean("passwordHasError", false)
            showPassword = it.getBoolean("showPassword", false)
            repasswordText = it.getString("repasswordText", "")
            repasswordHasError = it.getBoolean("repasswordHasError", false)
            showRepassword = it.getBoolean("showRepassword", false)
        }
    }
)

@Composable
fun rememberSignUpFormState(
    text: String,
    hasError: Boolean,
    showPassword: Boolean,
): SignUpFormState = rememberSaveable(
    saver = signUpFormSaver
) {
    SignUpFormState(text, hasError, showPassword)
}


@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    state: SignUpFormState = rememberSignUpFormState(
        text = "",
        hasError = false,
        showPassword = false,
    ),
) {
    val focusManager = LocalFocusManager.current
    val minPasswordLength = 8

    Column (
        modifier = modifier,
    ) {
        InputTextField(
            label = stringResource(id = R.string.name),
            text = state.nameText,
            hasError = state.nameHasError,
            onValueChange = { newText ->
                state.nameText = newText
                val nameRegex = Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9\\s]+\$")
                state.nameHasError = !nameRegex.matches(state.nameText)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down,
                    )
                }
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        EmailTextField(
            text = state.emailText,
            hasError = state.emailHasError,
            onValueChange = { newText: String ->
                state.emailText = newText
                val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
                state.emailHasError = !emailRegex.matches(state.emailText)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down,
                    )
                }
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            text = state.passwordText,
            hasError = state.passwordHasError,
            showPassword = state.showPassword,
            onValueChange = { newText: String ->
                state.passwordText = newText
                val passwordRegex = Regex("^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$")

                state.passwordHasError = state.passwordText.length < minPasswordLength || !passwordRegex.matches(state.passwordText)
            },
            onClick = { state.showPassword = !state.showPassword },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down,
                    )
                }
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        ConfirmPasswordTextField(
            confirmText = state.repasswordText,
            hasError = state.repasswordHasError,
            showPassword = state.showRepassword,
            onValueChange = { newText: String ->
                state.repasswordText = newText
                state.repasswordHasError = state.repasswordText != state.passwordText || state.repasswordText.length < minPasswordLength
            },
            onClick = { state.showRepassword = !state.showRepassword },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
    }
}