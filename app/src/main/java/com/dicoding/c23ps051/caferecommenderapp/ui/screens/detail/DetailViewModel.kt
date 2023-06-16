package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Cafe>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cafe>> get() = _uiState

    private val _mapsUiState: MutableStateFlow<UiState<LatLng>> = MutableStateFlow(UiState.Loading)
    val mapsUiState: StateFlow<UiState<LatLng>> get() = _mapsUiState

    private val _favoriteState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val favoriteState: StateFlow<Boolean> get() = _favoriteState

    fun getCafeById(id: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            pref.getToken().collect { token ->
                try {
                    val cafe = ApiConfig.getApiService()
                        .getCafeById("Bearer $token", id)
                    if (cafe.status == 200) {
                        val data = cafe.data
                        val result = Cafe(
                            id = data.cafe_id,
                            address = data.address,
                            closingHour = data.closing_hour,
                            description = data.description,
                            name = data.name,
                            openingHour = data.opening_hour,
                            priceCategory = data.price_category,
                            rating = data.rating as Double,
                            region = data.region,
                            review = data.review,
                            thumbnail = data.thumbnail_url,
                            facilities = listOf(
                                Facility.ALCOHOL to data.alcohol,
                                Facility.INDOOR to data.indoor,
                                Facility.IN_MALL to data.in_mall,
                                Facility.KID_FRIENDLY to data.kid_friendly,
                                Facility.LIVE_MUSIC to data.live_music,
                                Facility.OUTDOOR to data.outdoor,
                                Facility.PET_FRIENDLY to data.pet_friendly,
                                Facility.PARKING_AREA to data.parking_area,
                                Facility.RESERVATION to data.reservation,
                                Facility.SMOKING_AREA to data.smoking_area,
                                Facility.TAKEAWAY to data.takeaway,
                                Facility.TOILETS to data.toilets,
                                Facility.VIP_ROOM to data.vip_room,
                                Facility.WIFI to (data.wifi != 0),
                            )
                        )
                        getIsCafeFavorite(id, result)
                    } else {
                        _uiState.value = UiState.Error("Failed to load cafe")
                    }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(e.message.toString())
                }
            }
        }
    }

    fun getLocationFromAddress(context: Context, address: String) {
        _mapsUiState.value = UiState.Loading
        val geocoder = Geocoder(context)

        try {
            viewModelScope.launch {
                val addresses = geocoder.getFromLocationName(address, 1) as List<Address>
                if (addresses.isEmpty()) {
                    _mapsUiState.value = UiState.Error(context.getString(R.string.location_not_found))
                } else {
                    val location = addresses[0]
                    _mapsUiState.value =
                        UiState.Success(LatLng(location.latitude, location.longitude))
                }
            }
        } catch(e: Exception) {
            _mapsUiState.value = UiState.Error(context.getString(R.string.failed_to_get_location))
        }
    }

    fun addToFavorite(cafeId: String) {
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().addToFavorite("Bearer ${it.token}", it.userId, cafeId)
                    if (response.status == 200) _favoriteState.value = true
                }
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, e.message.toString())
            }
        }
    }

    fun removeFromFavorite(cafeId: String) {
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().removeFromFavorite("Bearer ${it.token}", it.userId, cafeId)
                    if (response.status == 200) _favoriteState.value = false
                }
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, e.message.toString())
            }
        }
    }

    private fun getIsCafeFavorite(cafeId: String, cafe: Cafe) {
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().getFavoritesByUserId("Bearer ${it.token}")

                    if (response.status == 200) {
                        val data = response.result
                        val result = data.filter { item -> item.cafe_id == cafeId }
                        _favoriteState.value = result.isNotEmpty()
                        _uiState.value = UiState.Success(cafe)
                    }
                }
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, e.message.toString())
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}