package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@Composable
fun ButtonSearchBar(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onCLick() }
            .background(MaterialTheme.colorScheme.background)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    placeholder: @Composable () -> Unit = { Text("Search") },
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    onSearch: () -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = shape,
        value = text,
        placeholder = placeholder,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(
                    painterResource(id = R.drawable.search),
                    contentDescription = stringResource(id = R.string.start_search)
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilledSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    placeholder: @Composable () -> Unit = { Text("Search") },
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onSearch: () -> Unit = {},
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        shape = shape,
        value = text,
        placeholder = placeholder,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Gray,
            unfocusedTextColor = Gray,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
    )
}

@Preview
@Composable
fun SearchBarFilledPreview() {
    CafeRecommenderAppTheme {
        ButtonSearchBar()
    }
}

@Preview
@Composable
fun SearchBarOutlinedPreview() {
    CafeRecommenderAppTheme {
        OutlinedSearchBar(Modifier, "Preview")
    }
}