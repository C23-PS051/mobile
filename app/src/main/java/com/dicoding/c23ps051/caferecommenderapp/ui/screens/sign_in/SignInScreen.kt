package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.constants.MIN_PASSWORD_LENGTH
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.AuthViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.PreferenceViewModelFactory
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppLogo
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppName
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ForgotPassword
import com.dicoding.c23ps051.caferecommenderapp.ui.components.GoogleButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OrDivider
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ProgressBar
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ToSignUpText
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.STARTER_CONTENT_PADDING
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignInScreen(
    navigateUp: () -> Unit,
    navigateToSignUp: () -> Unit,
    userPreference: UserPreference,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = viewModel(factory = PreferenceViewModelFactory(userPreference)),
    state: SignInState = rememberSignInState(
        text = "",
        hasError = false,
        showPassword = false,
        showErrorDialog = false,
        errorMessage = "",
        showProgressBar = false,
    ),
    authViewModel: AuthViewModel = viewModel()
) {
    val googleSignInClient: GoogleSignInClient
    val auth: FirebaseAuth = Firebase.auth
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    googleSignInClient = authViewModel.initGoogleSignInClient(context)

    fun firebaseAuthWithGoogle(idToken: String) {
        state.showProgressBar = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.showProgressBar = false
                    val user = auth.currentUser
                    if (user != null) {
                        signInViewModel.signIn(
                            user.displayName,
                            user.email as String,
                            idToken,
                            user.photoUrl.toString(),
                        )
                    }
                } else {
                    state.showProgressBar = false
                    state.showErrorDialog = true
                    state.errorMessage = context.getString(R.string.please_try_again)
                }
            }
    }

    val resultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                state.showErrorDialog = true
                state.errorMessage = context.getString(R.string.please_try_again)
            }
        }
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    // Configure Email Sign In
    fun firebaseAuthWithEmail(email: String, password: String) {
        state.showProgressBar = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.showProgressBar = false
                    val user = auth.currentUser
                    /*TODO: FIX THE TOKEN*/
                    if (user != null) {
                        signInViewModel.signIn(
                            user.displayName,
                            user.email as String,
                            "idToken",
                            user.photoUrl.toString(),
                        )
                    }
                } else {
                    state.showProgressBar = false
                    state.showErrorDialog = true
                    state.errorMessage = context.getString(R.string.wrong_email_password)
                }
            }
    }

//    SignInContent(
//        navigateUp = navigateUp,
//        navigateToSignUp = navigateToSignUp,
//        signInWithEmail = { firebaseAuthWithEmail(state.emailText, state.passwordText) },
//        signInWithGoogle = { signInWithGoogle() },
//    )

    if (state.showErrorDialog) {
        AlertDialog(
            confirmButton = {
                TextButton(onClick = { state.showErrorDialog = false }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            onDismissRequest = { state.showErrorDialog = false },
            title = {
                Text(text = stringResource(id = R.string.cannot_sign_in))
            },
            text = {
                Text(text = state.errorMessage)
            },
        )
    }

    if (state.showProgressBar) { ProgressBar() }

    Column {
        BackButton(
            onClick = { navigateUp() },
            modifier = Modifier.padding(APP_CONTENT_PADDING)
        )
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = STARTER_CONTENT_PADDING),
            verticalArrangement = Arrangement.Center,
        ) {
            AppLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
            AppName()
            Spacer(modifier = Modifier.height(48.dp))
            SignInForm(
                focusManager = focusManager,
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
                onVisibilityClick = {
                    state.showPassword = !state.showPassword
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(text = stringResource(id = R.string.sign_in)) {
                focusManager.clearFocus()
                val isInputValid = isInputValid(state.emailText, state.passwordText)
                if (isInputValid && !state.emailHasError && !state.passwordHasError) {
                    firebaseAuthWithEmail(state.emailText, state.passwordText)
                } else {
                    state.showErrorDialog = true
                    state.errorMessage = context.getString(R.string.check_your_input)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ForgotPassword()
            Spacer(modifier = Modifier.height(48.dp))
            OrDivider()
            Spacer(modifier = Modifier.height(48.dp))
            GoogleButton(
                onClick = { signInWithGoogle() }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ToSignUpText {
                navigateToSignUp()
            }
        }
    }
}

fun isInputValid(email: String, password: String): Boolean {
    return email != "" && password != ""
}

//@Composable
//fun SignInContent(
//    navigateUp: () -> Unit,
//    navigateToSignUp: () -> Unit,
//    signInWithEmail: () -> Unit,
//    signInWithGoogle: () -> Unit,
//    modifier: Modifier = Modifier,
//    state: SignInFormState = rememberSignInFormState(
//        text = "",
//        hasError = false,
//        showPassword = false,
//    ),
//) {
//    Column(
//        modifier = modifier
//            .verticalScroll(rememberScrollState())
//            .padding(STARTER_CONTENT_PADDING),
//        verticalArrangement = Arrangement.Center,
//    ) {
//        BackButton(onClick = { navigateUp() })
//        AppLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
//        AppName()
//        Spacer(modifier = Modifier.height(48.dp))
//        SignInForm(
//            emailText = state.emailText,
//            emailHasError = state.emailHasError,
//            emailOnValueChange = { newText: String ->
//                state.emailText = newText
//                val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
//                state.emailHasError = !emailRegex.matches(state.emailText)
//            },
//            passwordText = state.passwordText,
//            passwordHasError = state.passwordHasError,
//            showPassword = state.showPassword,
//            passwordOnValueChange = { newText: String ->
//                state.passwordText = newText
//                val passwordRegex = Regex("^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$")
//
//                state.passwordHasError = state.passwordText.length < MIN_PASSWORD_LENGTH || !passwordRegex.matches(state.passwordText)
//            },
//            onVisibilityClick = {
//                state.showPassword = !state.showPassword
//            }
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//        Button(text = stringResource(id = R.string.sign_in)) {
//            signInWithEmail()
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        ForgotPassword()
//        Spacer(modifier = Modifier.height(48.dp))
//        OrDivider()
//        Spacer(modifier = Modifier.height(48.dp))
//        GoogleButton(
//            onClick = { signInWithGoogle() }
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//        ToSignUpText {
//            navigateToSignUp()
//        }
//    }
//}

class SignInState(
    initialTextState: String,
    initialHasErrorState: Boolean,
    initialShowPasswordState: Boolean,
    initialShowDialogErrorState: Boolean,
    initialErrorMessageState: String,
    initialShowProgressBarState: Boolean,
) {
    /* Email Field State */
    var emailText by mutableStateOf(initialTextState)
    var emailHasError by mutableStateOf(initialHasErrorState)

    /* Password Field State */
    var passwordText by mutableStateOf(initialTextState)
    var passwordHasError by mutableStateOf(initialHasErrorState)
    var showPassword by mutableStateOf(initialShowPasswordState)

    /* Other State */
    var showErrorDialog by mutableStateOf(initialShowDialogErrorState)
    var errorMessage by mutableStateOf(initialErrorMessageState)
    var showProgressBar by mutableStateOf(initialShowProgressBarState)
}

@Composable
fun rememberSignInState(
    text: String,
    hasError: Boolean,
    showPassword: Boolean,
    showErrorDialog: Boolean,
    errorMessage: String,
    showProgressBar: Boolean,
): SignInState = remember(
    text, hasError, showPassword, showErrorDialog, errorMessage, showProgressBar
) {
    SignInState(text, hasError, showPassword, showErrorDialog, errorMessage, showProgressBar)
}