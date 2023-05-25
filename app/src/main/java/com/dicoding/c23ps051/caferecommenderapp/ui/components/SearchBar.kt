package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red

@Composable
fun FilledSearchBar(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onCLick() }
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
            )
            Text(
                text = stringResource(id = R.string.search_hint),
                color = Gray,
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            )
        }
    }
}

//@Composable
//fun OutlinedSearchBar(
//    modifier: Modifier = Modifier,
//) {
//    Surface(
//        modifier = modifier
//            .clip(RoundedCornerShape(12.dp))
//            .border(
//                width = 1.dp,
//                color = Gray,
//                shape = RoundedCornerShape(12.dp),
//            )
//            .padding(12.dp)
//    ) {
//        Row {
//
//            Text(
//                text = stringResource(id = R.string.search_hint),
//                color = Gray,
//                modifier = modifier
//                    .padding(horizontal = 4.dp)
//                    .weight(1f)
//            )
//            Image(
//                painter = painterResource(id = R.drawable.search),
//                contentDescription = null,
//            )
//        }
//    }
//}

@Composable
fun OutlinedSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    placeholder: @Composable () -> Unit = { Text("Search") },
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onSearch: () -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = shape,
        value = text,
        placeholder = placeholder,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Gray,
            textColor = Gray,
            cursorColor = Gray,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSearch() }
        ),
    )
}

@Preview
@Composable
fun SearchBarFilledPreview() {
    CafeRecommenderAppTheme {
        FilledSearchBar()
    }
}

@Preview
@Composable
fun SearchBarOutlinedPreview() {
    CafeRecommenderAppTheme {
        OutlinedSearchBar(Modifier, "Preview")
    }
}