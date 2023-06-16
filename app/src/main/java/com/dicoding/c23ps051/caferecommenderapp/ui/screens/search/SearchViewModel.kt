package com.dicoding.c23ps051.caferecommenderapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<String>> get() = _uiState

    private val _resultState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Initial)
    val resultState: StateFlow<ResultState> get() = _resultState

    fun getLocation() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            pref.getUserLocation()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { location ->
                    _uiState.value = UiState.Success(location)
                }
        }
    }

    fun saveLocation(location: String) {
        viewModelScope.launch {
            pref.setUserLocation(location)
        }
    }

    fun editCafePreference(preferences: List<Pair<Facility, Boolean>>, priceCategory: String) {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            val pref = pref.getLogin().first()

            val user = User(
                cafe_alcohol = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_in_mall = preferences.first { it.first == Facility.IN_MALL }.second,
                cafe_indoor = preferences.first { it.first == Facility.INDOOR }.second,
                cafe_kid_friendly = preferences.first { it.first == Facility.KID_FRIENDLY }.second,
                cafe_live_music = preferences.first { it.first == Facility.LIVE_MUSIC }.second,
                cafe_outdoor = preferences.first { it.first == Facility.OUTDOOR }.second,
                cafe_parking_area = preferences.first { it.first == Facility.PARKING_AREA }.second,
                cafe_pet_friendly = preferences.first { it.first == Facility.PET_FRIENDLY }.second,
                cafe_price_category = priceCategory,
                cafe_reservation = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_smoking_area = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_takeaway = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_toilets = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_vip_room = preferences.first { it.first == Facility.ALCOHOL }.second,
                cafe_wifi = preferences.first { it.first == Facility.ALCOHOL }.second,
                username = pref.userId,
            )

            val result = ApiConfig.getApiService().editProfile(pref.token, pref.userId, user)
            if (result.status == 200) {
                _resultState.value = ResultState.Success
            } else {
                _resultState.value = ResultState.Error("Failed to save preference")
            }
        }
    }
}