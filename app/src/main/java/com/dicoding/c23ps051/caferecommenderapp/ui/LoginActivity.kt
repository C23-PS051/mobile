package com.dicoding.c23ps051.caferecommenderapp.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppLogo
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppName
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.EmailTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ForgotPassword
import com.dicoding.c23ps051.caferecommenderapp.ui.components.GoogleButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.InputTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OrDivider
import com.dicoding.c23ps051.caferecommenderapp.ui.components.PasswordTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ToSignUpText
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Cream
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeRecommenderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        AppLogo(modifier = modifier.align(Alignment.CenterHorizontally))
        AppName()
        Spacer(modifier = Modifier.height(48.dp))
        EmailTextField()
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField()
        Spacer(modifier = Modifier.height(24.dp))
        Button(text = stringResource(id = R.string.sign_in)) {
            /* TODO */
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

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CafeRecommenderAppTheme {
        Login()
    }
}