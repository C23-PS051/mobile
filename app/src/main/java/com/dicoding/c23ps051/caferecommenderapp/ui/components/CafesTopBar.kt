package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun CafesTopBar(
    text: String,
    onQueryChange: (String) -> Unit,
    title: String,
    focusManager: FocusManager,
    selectedText: String,
    expanded: Boolean,
    setSelectedTextState: (String) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
) {
    Column (
        modifier = modifier
            .padding(horizontal = APP_CONTENT_PADDING)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                BackButton(
                    onClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            TopBarTitle(title = title)
        }
        Spacer(modifier = Modifier.height(12.dp))
        CafesTopBarOutlinedSearchBar(
            text = text,
            onQueryChange = onQueryChange,
            focusManager = focusManager,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
//            modifier = Modifier
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CafesTopBarDropDown(
                expanded = expanded,
                selectedText = selectedText,
                setExpandedState = setExpandedState,
                setSelectedTextState = setSelectedTextState,
            )
            Chip(text = stringResource(id = R.string.popular))
            Spacer(modifier = Modifier.height(4.dp))
            Chip(text = stringResource(id = R.string.open))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CafesTopBarOutlinedSearchBar(
    text: String,
    onQueryChange: (String) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
) {
    OutlinedSearchBar(
        modifier = modifier,
        text = text,
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        onValueChange = onQueryChange,
        onClick = {
            focusManager.clearFocus()
            /*TODO*/
        },
        onSearch = {
            focusManager.clearFocus()
            /* TODO */
        }
    )
}

@Composable
fun CafesTopBarDropDown(
    modifier: Modifier = Modifier,
    selectedText: String,
    expanded: Boolean,
    setSelectedTextState: (String) -> Unit,
    setExpandedState: (Boolean) -> Unit,
) {
    val options = listOf(
        stringResource(id = R.string.highest_rating),
        stringResource(id = R.string.nearest),
        stringResource(id = R.string.lowest_price_range),
        stringResource(id = R.string.highest_price_range),
    )

    val actions = options.map { option ->
        {
            if (selectedText != option) {
                setSelectedTextState(option)
            }
            setExpandedState(false)
        }
    }

    OutlinedDropDownTextField(
        modifier = modifier,
        text = selectedText,
        onClick = {
            setExpandedState(!expanded)
        },
        notOnFocus = !expanded
    )
    
    Spacer(modifier = Modifier.height(4.dp))

    OutlinedDropDown(
        expanded = expanded,
        options = options,
        actions = actions,
    )
}

//@Preview
//@Composable
//fun CafesTopBarPreview() {
//    CafeRecommenderAppTheme {
//        CafesTopBar(title = "Recommended for you")
//    }
//}