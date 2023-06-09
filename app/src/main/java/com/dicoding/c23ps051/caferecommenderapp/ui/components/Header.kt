package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    userLocation: String?,
    photoUrl: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = null
        )
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        ){
            Text(
                text = stringResource(id = R.string.location),
                fontSize = 12.sp
            )
            Text(
                text = userLocation ?: stringResource(id = R.string.not_set),
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
            )
        }
        ProfilePicture(
            image = photoUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CafeRecommenderAppTheme {
        Header("Jakarta Selatan", "", {})
    }
}