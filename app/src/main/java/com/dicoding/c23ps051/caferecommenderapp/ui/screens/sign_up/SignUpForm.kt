package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ConfirmPasswordTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.EmailTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.InputTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordTextField

@Composable
fun SignUpForm(
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
    repasswordText: String,
    repasswordHasError: Boolean,
    showRepassword: Boolean,
    onRepasswordValueChange: (String) -> Unit,
    onRepasswordTrailingIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column (
        modifier = modifier,
    ) {
        InputTextField(
            label = stringResource(id = R.string.name),
            text = nameText,
            hasError = nameHasError,
            enableErrorCheck = true,
            onValueChange = onNameValueChange,
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
            text = emailText,
            hasError = emailHasError,
            enableErrorCheck = true,
            onValueChange = onEmailValueChange,
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
            enableErrorCheck = true,
            showPassword = showPassword,
            onValueChange = onPasswordValueChange,
            onClick = onPasswordTrailingIconClick,
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
            confirmText = repasswordText,
            hasError = repasswordHasError,
            enableErrorCheck = true,
            showPassword = showRepassword,
            onValueChange = onRepasswordValueChange,
            onClick = onRepasswordTrailingIconClick,
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