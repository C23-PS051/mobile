package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun ToSignUpText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append(stringResource(id = R.string.no_account))
        }
        append(" ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
        ) {
            append(stringResource(id = R.string.sign_up_now))
        }
    }

    Spacer(modifier = modifier.height(8.dp))
    ClickableText(
        text = text,
        onClick = onClick,
    )
}

@Preview
@Composable
fun ToSignUpTextPreview() {
    CafeRecommenderAppTheme {
        ToSignUpText()
    }
}