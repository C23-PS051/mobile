package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R

@Composable
fun ForgotPassword(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.forgot_password),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth()
    )
}