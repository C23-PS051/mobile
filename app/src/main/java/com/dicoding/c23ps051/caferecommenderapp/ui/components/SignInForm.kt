package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun SignInForm() {

    val focusManager = LocalFocusManager.current
    val minPasswordLength = 8

    /* Email Field State */
    var emailText by rememberSaveable { mutableStateOf("") }
    var emailHasError by rememberSaveable { mutableStateOf(false) }

    /* Password Field State */
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordHasError by rememberSaveable { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    EmailTextField(
        text = emailText,
        hasError = emailHasError,
        onValueChange = { newText: String ->
            emailText = newText
            val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
            emailHasError = !emailRegex.matches(emailText)
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
        text = passwordText,
        hasError = passwordHasError,
        showPassword = showPassword,
        onValueChange = { newText: String ->
            passwordText = newText
            val passwordRegex = Regex("^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$")

            passwordHasError = passwordText.length < minPasswordLength || !passwordRegex.matches(passwordText)
        },
        trailingIcon = {
            val icon = if (showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.visibility),
                )
            }
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
}