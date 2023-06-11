package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CafeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Cafe>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cafe>> get() = _uiState

    private val _mapsUiState: MutableStateFlow<UiState<LatLng>> = MutableStateFlow(UiState.Loading)
    val mapsUiState: StateFlow<UiState<LatLng>> get() = _mapsUiState

    fun getCafeById(id: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCafeById(id))
        }
    }

    fun getLocationFromAddress(context: Context, address: String) {
        _mapsUiState.value = UiState.Loading
        val geocoder = Geocoder(context)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val addresses = geocoder.getFromLocationName(address, 1) as List<Address>
                if (addresses.isEmpty()) {
                    _mapsUiState.value = UiState.Error(context.getString(R.string.failed_to_get_location))
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
}