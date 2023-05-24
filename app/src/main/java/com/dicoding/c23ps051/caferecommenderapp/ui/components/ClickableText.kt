package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp),
    )
}

@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp),
    )
}

@Preview
@Composable
fun ClickableTextPreview() {
    CafeRecommenderAppTheme {
        ClickableText(
            text = "Some texts",
            onClick = {
                /* TODO */
            }
        )
    }
}