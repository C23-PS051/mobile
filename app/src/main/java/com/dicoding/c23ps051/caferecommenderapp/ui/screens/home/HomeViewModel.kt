package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponseItem
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    private val _cafesState: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val cafesState: StateFlow<UiState<List<Cafe>>> get() = _cafesState

    private val _uiStateMyRegion: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateMyRegion: StateFlow<UiState<List<Cafe>>> get() = _uiStateMyRegion

    private val _uiStateOpen24Hours: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOpen24Hours: StateFlow<UiState<List<Cafe>>> get() = _uiStateOpen24Hours

    private val _uiStatePopular: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStatePopular: StateFlow<UiState<List<Cafe>>> get() = _uiStatePopular

    val location get() = pref.getUserLocation()

    val profileUri get() = pref.getPhotoUrl()

    fun getAllCafes() {
        viewModelScope.launch {
            pref.getLogin().collect { login ->
                val request = viewModelScope.launch {
                    try {
                        val cafes = ApiConfig.getApiService()
                            .getAllCafes("Bearer ${login.token}")
                        if (cafes.status == 200) {
                            val result = mapCafes(cafes.data)
                            _cafesState.value = UiState.Success(result)
                        }
                    } catch (e: Exception) {
                        _cafesState.value = UiState.Error(e.message.toString())
                    }
                }

                if (login.token == "") request.cancel()
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

    fun filterByRegion(result: List<Cafe>) {
        viewModelScope.launch {
            location.catch {
                _uiStateMyRegion.value = UiState.Error(it.message.toString())
            }.collect { location ->
                val filteredCafes = if (location != "All") {
                    result.filter { it.region == location }
                } else {
                    result
                }
                _uiStateMyRegion.value = UiState.Success(filteredCafes)
            }
        }
    }

    fun filterByOpen24Hours(result: List<Cafe>) {
        val filteredCafes = result.filter { it.openingHour == 0 && it.closingHour == 24 }
        _uiStateOpen24Hours.value = UiState.Success(filteredCafes)
    }

    fun filterByPopular(result: List<Cafe>) {
        val modifiedResult = result.map { it.copy(review = it.review.replace(",", "")) }
        val filteredCafes = modifiedResult.filter { it.review.toInt() >= 1000 }
        _uiStatePopular.value = UiState.Success(filteredCafes)
    }

    fun setMyRegionCafesToError() {
        _uiStateMyRegion.value = UiState.Error("Failed to get cafes")
    }

    fun setOn24HoursCafesToError() {
        _uiStateOpen24Hours.value = UiState.Error("Failed to get cafes")
    }

    fun setPopularCafesToError() {
        _uiStatePopular.value = UiState.Error("Failed to get cafes")
    }
}