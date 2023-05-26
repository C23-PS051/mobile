package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Black
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.LightGray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.MildWhite
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.White

@Composable
fun Button(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = White,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(color),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }
}

@Composable
fun GoogleButton() {
    Box (
        contentAlignment = Alignment.CenterStart,
    ){
        Button(
            text = stringResource(id = R.string.google_sign_in),
            color = LightGray,
            textColor = Black,
        ) {
            /* TODO */
        }
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = null,
            modifier = Modifier.size(24.dp).offset(x = 16.dp)
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    CafeRecommenderAppTheme {
        Button(onClick = { /*TODO*/ }) {

        }
    }
}

@Preview
@Composable
fun GoogleButtonPreview() {
    CafeRecommenderAppTheme {
        GoogleButton()
    }
}