package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.common.isOpen
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecommendedViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cafe>>> get() = _uiState

    private var recommendedCafes = mutableListOf<Cafe>()
    private var filteredRecommendedCafes = listOf<Cafe>()

    private lateinit var location: String

    private val initialExpandedState = false
    private val initialSelectedTextState = 0
    private val initialRegionChipState = true
    private val initialOpenChipState = true

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

    init {
        viewModelScope.launch {
            pref.getUserLocation().collect {
                location = it
            }
        }

//        getRecommendedCafes()
    }

    fun getRecommendedCafes() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().getRecommendedCafes(it.token)

                    if (response.status == 200) {
                        val data = response.data
                        data.forEach { item ->
                            val cafe = Cafe(
                                id = item.cafeId,
                                address = item.address,
                                closingHour = item.closingHour,
                                description = item.description,
                                name = item.name,
                                openingHour = item.openingHour,
                                priceCategory = item.priceCategory,
                                rating = item.rating as Double,
                                region = item.region,
                                review = item.review,
                                thumbnail = item.thumbnailUrl,
                                facilities = listOf(
                                    Facility.ALCOHOL to item.alcohol,
                                    Facility.INDOOR to item.indoor,
                                    Facility.IN_MALL to item.inMall,
                                    Facility.KID_FRIENDLY to item.kidFriendly,
                                    Facility.LIVE_MUSIC to item.liveMusic,
                                    Facility.OUTDOOR to item.outdoor,
                                    Facility.PET_FRIENDLY to item.petFriendly,
                                    Facility.PARKING_AREA to item.parkingArea,
                                    Facility.RESERVATION to item.reservation,
                                    Facility.SMOKING_AREA to item.smokingArea,
                                    Facility.TAKEAWAY to item.takeaway,
                                    Facility.TOILETS to item.toilets,
                                    Facility.VIP_ROOM to item.vipRoom,
                                    Facility.WIFI to (item.wifi != 0),
                                )
                            )
                            recommendedCafes.add(cafe)
                        }
                        updateStates()
                    } else {
                        _uiState.value = UiState.Error("Failed loading cafes")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
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
        filteredRecommendedCafes = recommendedCafes

        if (query.value == "") {
            filteredRecommendedCafes.sortedBy { it.name }
        } else {
            filteredRecommendedCafes.filter {
                it.name.contains(query.value, ignoreCase = true)
            }
        }

        if (regionChip.value) {
            if (location != "All") {
                filteredRecommendedCafes = filteredRecommendedCafes.filter { it.region == location }
            }
        }
        if (openChip.value) {
            filteredRecommendedCafes = filteredRecommendedCafes.filter { isOpen(it.openingHour, it.closingHour) }
        }

//        filteredRecommendedCafes = when (selectedText.value) {
//            0 -> filteredRecommendedCafes.sortedBy { it.name }
//            1 -> filteredRecommendedCafes.sortedBy { it.distance }
//            2 -> filteredRecommendedCafes.sortedByDescending { it.rating }
//            3 -> filteredRecommendedCafes.sortedBy { it.maxPrice - it.minPrice }
//            4 -> filteredRecommendedCafes.sortedByDescending { it.maxPrice - it.minPrice }
//            else -> filteredRecommendedCafes
//        }

        _uiState.value = UiState.Success(filteredRecommendedCafes)
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