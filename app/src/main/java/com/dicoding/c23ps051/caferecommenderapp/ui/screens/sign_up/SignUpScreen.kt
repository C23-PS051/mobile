package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppLogo
import com.dicoding.c23ps051.caferecommenderapp.ui.components.AppName
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.ClickableText
import com.dicoding.c23ps051.caferecommenderapp.ui.components.SignUpForm
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.STARTER_CONTENT_PADDING

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit,
) {
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
            SignUpForm()
            Spacer(modifier = Modifier.height(24.dp))
            Button(text = stringResource(id = R.string.sign_up)) {
                /* TODO */
            }
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = stringResource(id = R.string.already_have_account),
                onClick = {
                    navigateToSignIn()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    CafeRecommenderAppTheme {
        SignUpScreen(
            navigateUp = {},
            navigateToSignIn = {}
        )
    }
}