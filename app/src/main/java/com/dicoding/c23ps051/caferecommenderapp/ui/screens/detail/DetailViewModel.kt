package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Cafe>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cafe>> get() = _uiState

    fun getCafeById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.getToken().collect { token ->
                val cafe = ApiConfig.getApiService()
                    .getCafeById("Bearer $token", id)
                if (cafe.status == 200) {
                    val data = cafe.data
                    val result = Cafe(
                        id = data.cafeId,
                        address = data.address,
                        closingHour = data.closingHour,
                        description = data.description,
                        name = data.name,
                        openingHour = data.openingHour,
                        priceCategory = data.priceCategory,
                        rating = data.rating as Double,
                        region = data.region,
                        review = data.review,
                        thumbnail = data.thumbnailUrl,
                        facilities = listOf(
                            Facility.ALCOHOL to data.alcohol,
                            Facility.INDOOR to data.indoor,
                            Facility.IN_MALL to data.inMall,
                            Facility.KID_FRIENDLY to data.kidFriendly,
                            Facility.LIVE_MUSIC to data.liveMusic,
                            Facility.OUTDOOR to data.outdoor,
                            Facility.PET_FRIENDLY to data.petFriendly,
                            Facility.PARKING_AREA to data.parkingArea,
                            Facility.RESERVATION to data.reservation,
                            Facility.SMOKING_AREA to data.smokingArea,
                            Facility.TAKEAWAY to data.takeaway,
                            Facility.TOILETS to data.toilets,
                            Facility.VIP_ROOM to data.vipRoom,
                            Facility.WIFI to (data.wifi != 0),
                        )
                    )
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error("Failed to load cafe")
                    Log.d("MyLogger", cafe.status.toString())
                }
            }
        }
    }
}