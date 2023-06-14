package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponse
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

//    fun getNearbyCafes() {
//        viewModelScope.launch {
//            // TODO: TO BE UPDATED (repository get function)
//            repository.getNearbyCafes()
//                .catch {
//                    _uiStateNearby.value = UiState.Error(it.message.toString())
//                }
//                .collect { cafes ->
//                    _uiStateNearby.value = UiState.Success(cafes)
//                }
//        }
//    }
//
//    fun getOpen24HoursCafes() {
//        // TODO: TO BE UPDATED (repository get function)
//        viewModelScope.launch {
//            repository.getOpen24HoursCafes()
//                .catch {
//                    _uiStateOpen24Hours.value = UiState.Error(it.message.toString())
//                }
//                .collect { cafes ->
//                    _uiStateOpen24Hours.value = UiState.Success(cafes)
//                }
//        }
//    }
//
//    fun getOnBudgetCafes() {
//        // TODO: TO BE UPDATED (repository get function)
//        viewModelScope.launch {
//            repository.getOnBudgetCafes()
//                .catch {
//                    _uiStateOnBudget.value = UiState.Error(it.message.toString())
//                }
//                .collect { cafes ->
//                    _uiStateOnBudget.value = UiState.Success(cafes)
//                }
//        }
//    }
}