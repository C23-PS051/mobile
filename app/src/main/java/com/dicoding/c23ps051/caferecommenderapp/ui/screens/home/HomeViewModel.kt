package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiStateNearby: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateNearby: StateFlow<UiState<List<Cafe>>> get() = _uiStateNearby

    private val _uiStateOpen24Hours: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(
        UiState.Loading)
    val uiStateOpen24Hours: StateFlow<UiState<List<Cafe>>> get() = _uiStateOpen24Hours

    private val _uiStateOnBudget: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOnBudget: StateFlow<UiState<List<Cafe>>> get() = _uiStateOnBudget

    fun getAllCafes() {
        viewModelScope.launch {
            pref.getToken().collect { token ->
                val cafes = ApiConfig.getApiService()
                    .getAllCafes("Bearer $token")
                if (cafes.status == 200) {
                    val result = mutableListOf<Cafe>()
                    val data = cafes.data
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
                        result.add(cafe)
                    }
                    _uiStateNearby.value = UiState.Success(result) // TODO: TO BE UPDATED
                    _uiStateOnBudget.value = UiState.Success(result)
                    _uiStateOpen24Hours.value = UiState.Success(result)
                } else {
                    _uiStateNearby.value = UiState.Error("Failed to load cafes")
                }
            }
        }
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