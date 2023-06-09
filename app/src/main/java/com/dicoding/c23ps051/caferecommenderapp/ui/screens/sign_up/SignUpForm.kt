package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ConfirmPasswordTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.EmailTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.InputTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.RadioGroup
import com.dicoding.c23ps051.caferecommenderapp.ui.components.RadioItem

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
    radioState: List<Boolean>,
    onRadioButtonClick: (Int) -> Unit,
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
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        RadioGroup(
            selected = radioState,
            items = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female)),
            onClick = onRadioButtonClick,
        )
    }
}