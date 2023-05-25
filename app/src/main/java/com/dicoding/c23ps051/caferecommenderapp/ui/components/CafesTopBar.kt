package com.dicoding.c23ps051.caferecommenderapp.ui.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme

@Composable
fun CafesTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = false,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = APP_CONTENT_PADDING)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                Back {
                    /* TODO */
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            TopBarTitle(title = title)
        }
        Spacer(modifier = Modifier.height(12.dp))
        CafesTopBarOutlinedSearchBar()
        Spacer(modifier = Modifier.height(8.dp))
        CafesTopBarDropDown()
        Spacer(modifier = Modifier.height(8.dp))
    }
}

class CafesTopBarOutlinedSearchBarState(
    initialSearchTextState: String,
    initialOnFocusState: Boolean,
) {
    var searchText by mutableStateOf(initialSearchTextState)
    var onFocus by mutableStateOf(initialOnFocusState)
}

val CafesTopBarOutlinedSearchBarSaver = Saver<CafesTopBarOutlinedSearchBarState, Bundle>(
    save = {
        bundleOf(
            "searchText" to it.searchText,
            "onFocus" to it.onFocus,
        )
    },
    restore = {
        CafesTopBarOutlinedSearchBarState(
            initialSearchTextState = it.getString("searchText", ""),
            initialOnFocusState = it.getBoolean("onFocus", false)
        ).apply { 
            searchText = it.getString("searchText", "")
            onFocus = it.getBoolean("onFocus", false)
        }
    }
)

@Composable
fun rememberCafesTopBarOutlinedSearchBarState(
    searchText: String,
    onFocus: Boolean,
): CafesTopBarOutlinedSearchBarState = rememberSaveable(
    saver = CafesTopBarOutlinedSearchBarSaver 
) {
    CafesTopBarOutlinedSearchBarState(searchText, onFocus)
}

@Composable
fun CafesTopBarOutlinedSearchBar(
    modifier: Modifier = Modifier,
    state: CafesTopBarOutlinedSearchBarState = rememberCafesTopBarOutlinedSearchBarState(
        searchText = "",
        onFocus = false,
    )
) {
    val focusManager = LocalFocusManager.current
//    OutlinedSearchBar(
//        text = state.searchText,
//        onFocus = state.onFocus,
//        trailingIcon = painterResource(id = R.drawable.search),
//    )
    Log.d("MyLogger", "log is working")
    OutlinedSearchBar(
        text = state.searchText,
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        onValueChange = { newText ->
            state.searchText = newText
        },
        trailingIcon = {
            IconButton(onClick = {
                focusManager.clearFocus()
                /* TODO */
            }) {
                Icon(painterResource(id = R.drawable.search), contentDescription = null)
            }
        },
        onSearch = {
            focusManager.clearFocus()
            /* TODO */
        }
//        onFocus = state.onFocus,
    )
}

class CafesTopBarDropDownState(
    initialExpandedState: Boolean,
    initialSelectedText: String,
) {
    var expanded by mutableStateOf(initialExpandedState)
    var selectedText by mutableStateOf(initialSelectedText)
}

val CafesTopBarDropDownSaver = Saver<CafesTopBarDropDownState, Bundle>(
    save = {
           bundleOf(
               "expanded" to it.expanded,
               "selectedText" to it.selectedText
           )
    },
    restore = {
          CafesTopBarDropDownState(
              initialExpandedState = it.getBoolean("expanded", false),
              initialSelectedText = it.getString("selectedText", "")
          ).apply {
              expanded = it.getBoolean("expanded", false)
              selectedText = it.getString("selectedText", "")
          }
    },
)

@Composable
fun rememberCafesTopBarDropDownState(
    expanded: Boolean,
    selectedText: String,
): CafesTopBarDropDownState = rememberSaveable(
    saver = CafesTopBarDropDownSaver
) {
    CafesTopBarDropDownState(expanded, selectedText)
}

@Composable
fun CafesTopBarDropDown(
    modifier: Modifier = Modifier,
    state: CafesTopBarDropDownState = rememberCafesTopBarDropDownState(
        expanded = false,
        selectedText = stringResource(id = R.string.sort_by)
    )
) {
    val options = listOf(
        stringResource(id = R.string.highest_rating),
        stringResource(id = R.string.nearest),
        stringResource(id = R.string.lowest_price_range),
        stringResource(id = R.string.highest_price_range),
    )

    val actions = options.map { option ->
        {
            if (state.selectedText != option) {
                state.selectedText = option
            }
            state.expanded = false
        }
    }

    OutlinedDropDownTextField(
        text = state.selectedText,
        onClick = {
            state.expanded = !state.expanded
        },
        notOnFocus = !state.expanded
    )
    
    Spacer(modifier = Modifier.height(4.dp))

    OutlinedDropDown(
        expanded = state.expanded,
        options = options,
        actions = actions,
    )
}

@Preview
@Composable
fun CafesTopBarPreview() {
    CafeRecommenderAppTheme {
        CafesTopBar(title = "Recommended for you")
    }
}