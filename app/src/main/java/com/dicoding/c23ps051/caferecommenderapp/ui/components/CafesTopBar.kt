package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING

@Composable
fun CafesTopBar(
    text: String,
    onQueryChange: (String) -> Unit,
    title: String,
    focusManager: FocusManager,
    selectedText: Int,
    expanded: Boolean,
    setSelectedTextState: (Int) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    isRegionChipChecked: Boolean,
    onRegionChipClicked: () -> Unit,
    isOpenChipChecked: Boolean,
    onOpenChipClicked: () -> Unit,
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
                onDismissRequest = onDismissRequest,
            )
            Chip(
                text = stringResource(id = R.string.my_region),
                isChecked = isRegionChipChecked,
                onClick = onRegionChipClicked,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Chip(
                text = stringResource(id = R.string.open),
                isChecked = isOpenChipChecked,
                onClick = onOpenChipClicked,
            )
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
    selectedText: Int,
    expanded: Boolean,
    setSelectedTextState: (Int) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = listOf(
        stringResource(id = R.string.sort_by),
        stringResource(id = R.string.highest_rating),
        stringResource(id = R.string.nearest),
        stringResource(id = R.string.lowest_price_range),
        stringResource(id = R.string.highest_price_range),
    )

    val actions = List(options.size) { index ->
        {
            if (selectedText != index) {
                setSelectedTextState(index)
            }
            setExpandedState(false)
        }
    }

    OutlinedDropDownTextField(
        modifier = modifier,
        text = options[selectedText],
        onClick = {
            setExpandedState(!expanded)
        },
        notOnFocus = !expanded,
        verticalPadding = 2,
        horizontalPadding = 8,
        fontSize = 12,
    )
    
    Spacer(modifier = Modifier.height(4.dp))

    OutlinedDropDown(
        expanded = expanded,
        options = options,
        actions = actions,
        onDismissRequest = onDismissRequest
    )
}

//@Preview
//@Composable
//fun CafesTopBarPreview() {
//    CafeRecommenderAppTheme {
//        CafesTopBar(title = "Recommended for you")
//    }
//}