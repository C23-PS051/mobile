package com.dicoding.c23ps051.caferecommenderapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppLogo
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppName
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.EmailTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ClickableText
import com.dicoding.c23ps051.caferecommenderapp.ui.components.GoogleButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OrDivider
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ToSignUpText
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

class SignInActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    SignIn()
                }
            }
        }
    }
}

@Composable
fun SignIn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        AppLogo(modifier = modifier.align(Alignment.CenterHorizontally))
        AppName()
        Spacer(modifier = Modifier.height(48.dp))
//        EmailTextField()
        Spacer(modifier = Modifier.height(16.dp))
//        PasswordTextField()
        Spacer(modifier = Modifier.height(24.dp))
        Button(text = stringResource(id = R.string.sign_in)) {
            /* TODO */
        }
        Spacer(modifier = Modifier.height(16.dp))
        ClickableText(
            text = stringResource(id = R.string.forgot_password),
            onClick = {
                /* TODO */
            }
        )
        Spacer(modifier = Modifier.height(48.dp))
        OrDivider()
        Spacer(modifier = Modifier.height(48.dp))
        GoogleButton()
        Spacer(modifier = Modifier.height(16.dp))
        ToSignUpText()
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    CafeRecommenderAppTheme {
        SignIn()
    }
}