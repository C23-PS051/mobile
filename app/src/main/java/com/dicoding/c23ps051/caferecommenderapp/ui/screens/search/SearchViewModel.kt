package com.dicoding.c23ps051.caferecommenderapp.ui.screens.search

import android.util.Log
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

    fun editCafePreference(preferences: List<Pair<Facility, Boolean>>) {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            pref.getLogin().collect {
                Log.d("MyLogger", "SearchViewModel: ${it.userId}")
                val response = ApiConfig.getApiService().getUserProfile(it.token, it.userId)

                if (response.status == 200) {
                    val data = response.data
                    val user = User(
                        email = data.email,
                        fullName = data.fullName,
                        photoUri = data.photoUri,
                        preferences = preferences
                    )

                    val result = ApiConfig.getApiService().editProfile(it.token, it.userId, user)
                    if (result.status == 200) {
                        _resultState.value = ResultState.Success
                    } else {
                        _resultState.value = ResultState.Error("Failed to save preference")
                    }
                } else {
                    _resultState.value = ResultState.Error("Failed to save preference")
                }
            }
        }
    }
}