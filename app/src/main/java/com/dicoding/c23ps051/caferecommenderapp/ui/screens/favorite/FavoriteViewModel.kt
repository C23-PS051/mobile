package com.dicoding.c23ps051.caferecommenderapp.ui.screens.favorite

import android.util.Log
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

class FavoriteViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cafe>>> get() = _uiState

    fun getFavoritesByUserId() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().getFavoritesByUserId(it.token, it.userId)

                    if (response.status == 200) {
                        val data = response.data
                        val result = mutableListOf<Cafe>()

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
                        _uiState.value = UiState.Success(result)
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