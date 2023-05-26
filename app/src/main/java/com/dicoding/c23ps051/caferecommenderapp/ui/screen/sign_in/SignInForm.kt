package com.dicoding.c23ps051.caferecommenderapp.ui.screen.sign_in

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
import com.dicoding.c23ps051.caferecommenderapp.ui.components.EmailTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordTextField

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    emailText: String,
    emailHasError: Boolean,
    emailOnValueChange: (String) -> Unit,
    passwordText: String,
    passwordHasError: Boolean,
    showPassword: Boolean,
    passwordOnValueChange: (String) -> Unit,
    passwordTrailingIcon: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    EmailTextField(
        text = emailText,
        hasError = emailHasError,
        onValueChange = emailOnValueChange,
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
        onValueChange = passwordOnValueChange,
        trailingIcon = passwordTrailingIcon,
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