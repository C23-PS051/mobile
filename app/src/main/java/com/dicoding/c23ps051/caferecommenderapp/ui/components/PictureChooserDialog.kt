package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray


@Composable
fun PictureChooserDialog(
    onTakePicture: () -> Unit,
    onChooseFromGallery: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(APP_CONTENT_PADDING)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.change_picture),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.picture_chooser),
                color = Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        PictureChooserOption(text = stringResource(id = R.string.take_picture)) { onTakePicture() }
        PictureChooserOption(text = stringResource(id = R.string.choose_from_gallery)) { onChooseFromGallery() }
        PictureChooserOption(text = stringResource(id = R.string.cancel)) { onCancel() }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PictureChooserOption(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    )
}