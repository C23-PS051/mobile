package com.dicoding.c23ps051.caferecommenderapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<String>> get() = _uiState

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
}