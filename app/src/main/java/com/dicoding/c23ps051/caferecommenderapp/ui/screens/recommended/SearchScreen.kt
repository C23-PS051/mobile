package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackButton
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Button
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Chip
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OutlinedDropDown
import com.dicoding.c23ps051.caferecommenderapp.ui.components.OutlinedDropDownTextField
import com.dicoding.c23ps051.caferecommenderapp.ui.components.Section
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.APP_CONTENT_PADDING

private const val facilitiesSize = 14
private const val ratingSize = 5
private const val priceSize = 3

@Composable
fun SearchScreen(
    region: String? = stringResource(id = R.string.all),
    navigateUp: () -> Unit,
    onSubmit: () -> Unit,
    state: SearchState = rememberSearchState(
        expanded = false,
        selectedText = region ?: stringResource(id = R.string.all),
        checkedCheckbox = List(facilitiesSize) { false },
        checkedRatingChip = List(ratingSize) { false },
        checkedPriceChip = List(priceSize) { false },
    )
) {
    SearchContent(
        selectedText = state.selectedText,
        expanded = state.expanded,
        setSelectedTextState = { selectedText ->
            state.selectedText = selectedText
        },
        setExpandedState = { expanded ->
            state.expanded = expanded
        },
        navigateUp = navigateUp,
        onDismissRequest = {
            state.expanded = false
        },
        onSubmit = onSubmit,
        checkedState = state.checkedCheckbox,
        toggleCheckbox = { index ->
            state.checkedCheckbox[index] = !state.checkedCheckbox[index]
        },
        onCheckedChange = { isChecked, index ->
            state.checkedCheckbox[index] = isChecked
        },
        isCheckedRatingChip = state.checkedRatingChip,
        onRatingChipClicked = { index ->
            state.checkedRatingChip[index] = !state.checkedRatingChip[index]
        },
        isCheckedPriceChip = state.checkedPriceChip,
        onPriceChipClicked = { index ->
            state.checkedPriceChip[index] = !state.checkedPriceChip[index]
        }
    )
}

class SearchState(
    initialExpandedState: Boolean,
    initialSelectedTextState: String,
    initialCheckedCheckboxState: List<Boolean>,
    initialCheckedRatingChipState: List<Boolean>,
    initialCheckedPriceChipState: List<Boolean>,
) {
    var expanded by mutableStateOf(initialExpandedState)
    var selectedText by mutableStateOf(initialSelectedTextState)
    var checkedCheckbox = mutableStateListOf<Boolean>().apply { addAll(initialCheckedCheckboxState) }
    var checkedRatingChip = mutableStateListOf<Boolean>().apply { addAll(initialCheckedRatingChipState) }
    var checkedPriceChip = mutableStateListOf<Boolean>().apply { addAll(initialCheckedPriceChipState) }
}

@Composable
fun rememberSearchState(
    expanded: Boolean,
    selectedText: String,
    checkedCheckbox: List<Boolean>,
    checkedRatingChip: List<Boolean>,
    checkedPriceChip: List<Boolean>,
): SearchState = remember(
    expanded, selectedText, checkedCheckbox, checkedRatingChip, checkedPriceChip
) {
    SearchState(expanded, selectedText, checkedCheckbox, checkedRatingChip, checkedPriceChip)
}

@Composable
fun SearchContent(
    selectedText: String,
    expanded: Boolean,
    setSelectedTextState: (String) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    navigateUp: () -> Unit,
    onSubmit: () -> Unit,
    checkedState: List<Boolean>,
    toggleCheckbox: (Int) -> Unit,
    onCheckedChange: (Boolean, Int) -> Unit,
    isCheckedRatingChip: List<Boolean>,
    onRatingChipClicked: (Int) -> Unit,
    isCheckedPriceChip: List<Boolean>,
    onPriceChipClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val ratings = listOf(
        stringResource(id = R.string.one_star_rating),
        stringResource(id = R.string.two_star_rating),
        stringResource(id = R.string.three_star_rating),
        stringResource(id = R.string.four_star_rating),
        stringResource(id = R.string.five_star_rating),
    )

    val priceRange = listOf(
        stringResource(id = R.string.low_price),
        stringResource(id = R.string.mid_price),
        stringResource(id = R.string.high_price),
    )

    val facilities = listOf(
        stringResource(id = R.string.indoor),
        stringResource(id = R.string.outdoor),
        stringResource(id = R.string.wifi),
        stringResource(id = R.string.kid_friendly),
        stringResource(id = R.string.pet_friendly),
        stringResource(id = R.string.takeaway),
        stringResource(id = R.string.smoking_area),
        stringResource(id = R.string.parking_area),
        stringResource(id = R.string.toilets),
        stringResource(id = R.string.live_music),
        stringResource(id = R.string.in_mall),
        stringResource(id = R.string.vip_room),
        stringResource(id = R.string.reservation),
        stringResource(id = R.string.alcohol),
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 8.dp)
            ) {
                BackButton(onClick = { navigateUp() })
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Button(text = stringResource(id = R.string.submit)) {
                    onSubmit()
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(APP_CONTENT_PADDING),
        ) {
            Text(
                text = stringResource(id = R.string.what_are_we_looking_for),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Section(
                title = stringResource(id = R.string.region),
                content = {
                    DropDown(
                        selectedText = selectedText,
                        expanded = expanded,
                        setSelectedTextState = setSelectedTextState,
                        setExpandedState = setExpandedState,
                        onDismissRequest = onDismissRequest,
                    )
                },
            )
            Section(
                title = stringResource(id = R.string.ratings),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ratings.forEachIndexed { index, rating ->
                            SearchChip(
                                text = rating,
                                isChecked = isCheckedRatingChip[index],
                                onClick = { onRatingChipClicked(index) }
                            )
                        }
                    }
                }
            )
            Section(
                title = stringResource(id = R.string.price_range),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        priceRange.forEachIndexed { index, price_range ->
                            SearchChip(
                                text = price_range,
                                isChecked = isCheckedPriceChip[index],
                                onClick = { onPriceChipClicked(index) }
                            )
                        }
                    }
                }
            )
            Section(
                title = stringResource(id = R.string.facilities),
                content = {
                    Column {
                        facilities.forEachIndexed { index, facility ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { toggleCheckbox(index) }
                            ) {
                                Checkbox(
                                    checked = checkedState[index],
                                    onCheckedChange = { isChecked ->
                                        onCheckedChange(isChecked, index)
                                    }
                                )
                                Text(
                                    text = facility
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun SearchChip(text: String, isChecked: Boolean, onClick: () -> Unit) {
    Chip(
        text = text,
        modifier = Modifier.height(36.dp),
        isChecked = isChecked,
        onClick = onClick,
    )
}

@Composable
fun DropDown(
    selectedText: String,
    expanded: Boolean,
    setSelectedTextState: (String) -> Unit,
    setExpandedState: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = listOf(
        stringResource(id = R.string.all),
        stringResource(id = R.string.central_jakarta),
        stringResource(id = R.string.south_jakarta),
        stringResource(id = R.string.north_jakarta),
        stringResource(id = R.string.west_jakarta),
        stringResource(id = R.string.east_jakarta),
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
        modifier = modifier
            .fillMaxWidth(),
        text = selectedText,
        onClick = {
            setExpandedState(!expanded)
        },
        notOnFocus = !expanded,
        verticalPadding = 8,
        horizontalPadding = 16,
        fontSize = 14,
    )

    Spacer(modifier = Modifier.height(4.dp))

    OutlinedDropDown(
        expanded = expanded,
        options = options,
        actions = actions,
        onDismissRequest = onDismissRequest
    )
}
