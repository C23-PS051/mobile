package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.response.CafeDetailResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponseItem
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: CafeRepository) : ViewModel() {

    private val _uiStateNearby: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateNearby: StateFlow<UiState<List<Cafe>>> get() = _uiStateNearby

    private val _uiStateOpen24Hours: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOpen24Hours: StateFlow<UiState<List<Cafe>>> get() = _uiStateOpen24Hours

    private val _uiStateOnBudget: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOnBudget: StateFlow<UiState<List<Cafe>>> get() = _uiStateOnBudget

    fun getAllCafes() {
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getAllCafes()
            client.enqueue(object : Callback<CafeResponse> {
                override fun onResponse(call: Call<CafeResponse>, response: Response<CafeResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            if (responseBody.status == 200) {
                                val result = mutableListOf<Cafe>()
                                val data = responseBody.data
                                data.forEach { item ->
                                    item.detail.forEach { detail ->
                                        val cafe = Cafe(
                                            id = item.id,
                                            address = detail.address,
                                            closingHour = detail.closingHour,
                                            description = detail.description,
                                            name = detail.name,
                                            openingHour = detail.openingHour,
                                            priceCategory = detail.priceCategory,
                                            rating = detail.rating,
                                            region = detail.region,
                                            review = detail.review,
                                            thumbnail = detail.thumbnailUrl,
                                            facilities = listOf(
                                                Facility.ALCOHOL to detail.alcohol,
                                                Facility.INDOOR to detail.indoor,
                                                Facility.IN_MALL to detail.inMall,
                                                Facility.KID_FRIENDLY to detail.kidFriendly,
                                                Facility.LIVE_MUSIC to detail.liveMusic,
                                                Facility.OUTDOOR to detail.outdoor,
                                                Facility.PET_FRIENDLY to detail.petFriendly,
                                                Facility.PARKING_AREA to detail.parkingArea,
                                                Facility.RESERVATION to detail.reservation,
                                                Facility.SMOKING_AREA to detail.smokingArea,
                                                Facility.TAKEAWAY to detail.takeaway,
                                                Facility.TOILETS to detail.toilets,
                                                Facility.VIP_ROOM to detail.vipRoom,
                                                Facility.WIFI to detail.wifi,
                                            )
                                        )
                                        result.add(cafe)
                                    }
                                }
                                _uiStateNearby.value = UiState.Success(result) // TODO: TO BE UPDATED
                            } else {

                            }
                        } else {

                        }
                    } else {

                    }
                }

                override fun onFailure(call: Call<CafeResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun getNearbyCafes() {
        viewModelScope.launch {
            // TODO: TO BE UPDATED (repository get function)
            repository.getNearbyCafes()
                .catch {
                    _uiStateNearby.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateNearby.value = UiState.Success(cafes)
                }
        }
    }

    fun getOpen24HoursCafes() {
        // TODO: TO BE UPDATED (repository get function)
        viewModelScope.launch {
            repository.getOpen24HoursCafes()
                .catch {
                    _uiStateOpen24Hours.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateOpen24Hours.value = UiState.Success(cafes)
                }
        }
    }

    fun getOnBudgetCafes() {
        // TODO: TO BE UPDATED (repository get function)
        viewModelScope.launch {
            repository.getOnBudgetCafes()
                .catch {
                    _uiStateOnBudget.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateOnBudget.value = UiState.Success(cafes)
                }
        }
    }
}