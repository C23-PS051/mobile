package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun ForgotPassword(
    modifier: Modifier = Modifier
) {
    ClickableText(
        text = stringResource(id = R.string.forgot_password),
        onClick = {
            /* TODO */
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun ForgotPasswordPreview() {
    CafeRecommenderAppTheme {
        ForgotPassword()
    }
}