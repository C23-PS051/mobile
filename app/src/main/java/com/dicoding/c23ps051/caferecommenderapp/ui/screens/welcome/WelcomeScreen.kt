package com.dicoding.c23ps051.caferecommenderapp.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Black
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.LightCream

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = modifier
                    .size(256.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = stringResource(id = R.string.welcome_text),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.welcome_description),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = stringResource(id = R.string.welcome_closing),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(64.dp))
            Button(text = stringResource(id = R.string.sign_in)) {
                navigateToSignIn()
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                text = stringResource(id = R.string.sign_up),
                color = LightCream,
                textColor = Black,
            ) {
                navigateToSignUp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        navigateToSignIn = {},
        navigateToSignUp = {}
    )
}