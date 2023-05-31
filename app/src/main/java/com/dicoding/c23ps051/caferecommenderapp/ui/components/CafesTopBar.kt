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
    modifier: Modifier = Modifier,
    title: String,
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
        CafesTopBarOutlinedSearchBar()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
//            modifier = Modifier
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CafesTopBarDropDown()
            Chip(text = stringResource(id = R.string.popular))
            Spacer(modifier = Modifier.height(4.dp))
            Chip(text = stringResource(id = R.string.open))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

class CafesTopBarOutlinedSearchBarState(
    initialSearchTextState: String,
) {
    var searchText by mutableStateOf(initialSearchTextState)
}

val CafesTopBarOutlinedSearchBarSaver = Saver<CafesTopBarOutlinedSearchBarState, Bundle>(
    save = {
        bundleOf(
            "searchText" to it.searchText,
        )
    },
    restore = {
        CafesTopBarOutlinedSearchBarState(
            initialSearchTextState = it.getString("searchText", ""),
        ).apply { 
            searchText = it.getString("searchText", "")
        }
    }
)

@Composable
fun rememberCafesTopBarOutlinedSearchBarState(
    searchText: String,
): CafesTopBarOutlinedSearchBarState = rememberSaveable(
    saver = CafesTopBarOutlinedSearchBarSaver 
) {
    CafesTopBarOutlinedSearchBarState(searchText)
}

@Composable
fun CafesTopBarOutlinedSearchBar(
    modifier: Modifier = Modifier,
    state: CafesTopBarOutlinedSearchBarState = rememberCafesTopBarOutlinedSearchBarState(
        searchText = "",
    )
) {
    val focusManager = LocalFocusManager.current

    OutlinedSearchBar(
        text = state.searchText,
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        onValueChange = { newText ->
            state.searchText = newText
        },
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