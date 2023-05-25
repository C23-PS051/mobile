package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(16.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = null
        )
        Column (
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        ){
            Text(
                text = stringResource(id = R.string.location),
                fontSize = 12.sp
            )
            Text(
                text = "City Name",
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { /* TODO */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CafeRecommenderAppTheme {
        Header()
    }
}