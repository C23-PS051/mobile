package com.dicoding.c23ps051.caferecommenderapp.ui.screens.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponseItem
import com.dicoding.c23ps051.caferecommenderapp.ui.common.isOpen
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavoriteViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState<List<Cafe>>> get() = _uiState

    private var favoriteCafes = mutableListOf<Cafe>()
    private var filteredFavoriteCafes = listOf<Cafe>()

    private lateinit var location: String

    private val initialExpandedState = false
    private val initialSelectedTextState = 0
    private val initialRegionChipState = false
    private val initialOpenChipState = false

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private var _expanded = mutableStateOf(initialExpandedState)
    val expanded: State<Boolean> get() = _expanded

    private var _selectedText = mutableStateOf(initialSelectedTextState)
    val selectedText: State<Int> get() = _selectedText

    private var _regionChip = mutableStateOf(initialRegionChipState)
    val regionChip: State<Boolean> get() = _regionChip

    private var _openChip = mutableStateOf(initialOpenChipState)
    val openChip: State<Boolean> get() = _openChip

    fun getFavoritesByUserId() {
        if (uiState.value != UiState.Loading) {
            _uiState.value = UiState.Loading
            viewModelScope.launch {
                try {
                    location = pref.getUserLocation().first()
                    pref.getLogin().collect {
                        val response = ApiConfig.getApiService().getFavoritesByUserId(it.token)

                        if (response.status == 200) {
                            val result = mapCafes(response.result)
                            result.forEach { cafe ->
                                favoriteCafes.add(cafe)
                            }
                            _uiState.value = UiState.Success(result)
                            updateStates()
                        } else {
                            _uiState.value = UiState.Error("Failed to get favorites")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(e.message.toString())
                }
            }
        }
    }

    private fun mapCafes(data: List<CafeResponseItem>): List<Cafe> {
        return data.map { item ->
            Cafe(
                id = item.cafe_id,
                address = item.address,
                closingHour = item.closing_hour,
                description = item.description,
                name = item.name,
                openingHour = item.opening_hour,
                priceCategory = item.price_category,
                rating = item.rating as Double,
                region = item.region,
                review = item.review,
                thumbnail = item.thumbnail_url,
                facilities = listOf(
                    Facility.ALCOHOL to item.alcohol,
                    Facility.INDOOR to item.indoor,
                    Facility.IN_MALL to item.in_mall,
                    Facility.KID_FRIENDLY to item.kid_friendly,
                    Facility.LIVE_MUSIC to item.live_music,
                    Facility.OUTDOOR to item.outdoor,
                    Facility.PET_FRIENDLY to item.pet_friendly,
                    Facility.PARKING_AREA to item.parking_area,
                    Facility.RESERVATION to item.reservation,
                    Facility.SMOKING_AREA to item.smoking_area,
                    Facility.TAKEAWAY to item.takeaway,
                    Facility.TOILETS to item.toilets,
                    Facility.VIP_ROOM to item.vip_room,
                    Facility.WIFI to (item.wifi != 0),
                )
            )
        }
    }

    fun searchCafes(newQuery: String) {
        _query.value = newQuery
        updateStates()
    }

    fun setExpandedState(value: Boolean) {
        _expanded.value = value
        updateStates()
    }

    fun setSelectedTextState(value: Int) {
        _selectedText.value = value
        updateStates()
    }

    fun setRegionChipState(value: Boolean) {
        _regionChip.value = value
        updateStates()
    }

    fun setOpenChipState(value: Boolean) {
        _openChip.value = value
        updateStates()
    }

    private fun updateStates() {
        filteredFavoriteCafes = favoriteCafes

        filteredFavoriteCafes = if (query.value == "") {
            filteredFavoriteCafes.sortedBy { it.name }
        } else {
            filteredFavoriteCafes.filter {
                it.name.contains(query.value, ignoreCase = true)
            }
        }

        if (regionChip.value) {
            if (location != "All") {
                filteredFavoriteCafes = filteredFavoriteCafes.filter { it.region == location }
            }
        }
        if (openChip.value) {
            filteredFavoriteCafes = filteredFavoriteCafes.filter { isOpen(it.openingHour, it.closingHour) }
        }

        filteredFavoriteCafes = when (selectedText.value) {
            0 -> filteredFavoriteCafes.sortedBy { it.name }
            1 -> filteredFavoriteCafes.sortedByDescending { it.rating }
            2 -> filteredFavoriteCafes.sortedByDescending { it.review.replace(",", "").toInt() }
            3 -> filteredFavoriteCafes.sortedWith(compareBy { cafe ->
                when (cafe.priceCategory) {
                    "$" -> 0
                    "$$$" -> 2
                    else -> 1
                }
            })
            4 -> filteredFavoriteCafes.sortedWith(compareBy { cafe ->
                when (cafe.priceCategory) {
                    "$" -> 2
                    "$$$" -> 0
                    else -> 1
                }
            })
            else -> filteredFavoriteCafes
        }

        _uiState.value = UiState.Success(filteredFavoriteCafes)
    }

    fun isDefaultStates(): Boolean {
        return expanded.value == initialExpandedState &&
                selectedText.value == initialSelectedTextState &&
                regionChip.value == initialRegionChipState &&
                openChip.value == initialOpenChipState
    }

    fun resetStates() {
        _expanded.value = initialExpandedState
        _selectedText.value = initialSelectedTextState
        _regionChip.value = initialRegionChipState
        _openChip.value = initialOpenChipState
        updateStates()
    }
}