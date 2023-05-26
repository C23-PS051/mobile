package com.dicoding.c23ps051.caferecommenderapp.ui.screen.sign_in

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.MIN_PASSWORD_LENGTH
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppLogo
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppName
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ForgotPassword
import com.dicoding.c23ps051.caferecommenderapp.ui.components.GoogleButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OrDivider
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ToSignUpText
import com.dicoding.c23ps051.caferecommenderapp.ui.screen.ViewModelFactory

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
fun SignInScreen(
    userPreference: UserPreference,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = viewModel(factory = ViewModelFactory(userPreference)),
    state: SignInFormState = rememberSignInFormState(
        text = "",
        hasError = false,
        showPassword = false,
    )
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        BackButton {
            /* TODO */
        }
        AppLogo(modifier = modifier.align(Alignment.CenterHorizontally))
        AppName()
        Spacer(modifier = Modifier.height(48.dp))
        SignInForm(
            emailText = state.emailText,
            emailHasError = state.emailHasError,
            emailOnValueChange = { newText: String ->
                state.emailText = newText
                val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
                state.emailHasError = !emailRegex.matches(state.emailText)
            },
            passwordText = state.passwordText,
            passwordHasError = state.passwordHasError,
            showPassword = state.showPassword,
            passwordOnValueChange = { newText: String ->
                state.passwordText = newText
                val passwordRegex = Regex("^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$")

                state.passwordHasError = state.passwordText.length < MIN_PASSWORD_LENGTH || !passwordRegex.matches(state.passwordText)
            },
            passwordTrailingIcon = {
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
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(text = stringResource(id = R.string.sign_in)) {
            /* TODO */
            signInViewModel.login(state.emailText, state.passwordText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ForgotPassword()
        Spacer(modifier = Modifier.height(48.dp))
        OrDivider()
        Spacer(modifier = Modifier.height(48.dp))
        GoogleButton()
        Spacer(modifier = Modifier.height(16.dp))
        ToSignUpText()
    }
}