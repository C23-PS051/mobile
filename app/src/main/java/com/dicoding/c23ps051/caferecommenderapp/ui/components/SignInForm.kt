package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.os.Bundle
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.dicoding.c23ps051.caferecommenderapp.R

class SignInFormState(
    initialTextState: String,
    initialHasErrorState: Boolean,
    initialShowPasswordState: Boolean,
) {
    /* Email Field State */
    var emailText by mutableStateOf(initialTextState)
    var emailHasError by mutableStateOf(initialHasErrorState)

    /* Password Field State */
    var passwordText by mutableStateOf(initialTextState)
    var passwordHasError by mutableStateOf(initialHasErrorState)
    var showPassword by mutableStateOf(initialShowPasswordState)
}

val signInFormSaver = Saver<SignInFormState, Bundle>(
    save = {
        bundleOf(
            "emailText" to it.emailText,
            "emailHasError" to it.emailHasError,
            "passwordText" to it.passwordText,
            "passwordHasError" to it.passwordHasError,
            "showPassword" to it.showPassword,
        )
    },
    restore = {
        SignInFormState(
            initialTextState = it.getString("text", ""),
            initialHasErrorState = it.getBoolean("hasError", false),
            initialShowPasswordState = it.getBoolean("showPassword", false)
        ).apply {
            emailText = it.getString("emailText", "")
            emailHasError = it.getBoolean("emailHasError", false)
            passwordText = it.getString("passwordText", "")
            passwordHasError = it.getBoolean("passwordHasError", false)
            showPassword = it.getBoolean("showPassword", false)
        }
    }
)


@Composable
fun rememberSignInFormState(
    text: String,
    hasError: Boolean,
    showPassword: Boolean,
): SignInFormState = rememberSaveable(
    saver = signInFormSaver
) {
    SignInFormState(text, hasError, showPassword)
}

@Composable
fun SignInForm(
    state: SignInFormState = rememberSignInFormState(
        text = "",
        hasError = false,
        showPassword = false,
    )
) {
    val focusManager = LocalFocusManager.current
    val minPasswordLength = 8

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
        trailingIcon = {
            val icon = if (state.showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { state.showPassword = !state.showPassword }) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.visibility),
                )
            }
        },
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