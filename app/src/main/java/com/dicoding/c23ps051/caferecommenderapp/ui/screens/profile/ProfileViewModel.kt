package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>> get() = _uiState

    fun getUserProfile() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            pref.getLogin().collect {
                val response = ApiConfig.getApiService().getUserProfile(it.token, it.userId)

                if (response.status == 200) {
                    val data = response.data

                    val user = User(
                        fullName = data.fullName,
                        email = data.email,
                        photoUri = data.photoUri,
                        preferences = data.preferences.map { preference ->
                            Facility.valueOf(preference.first) to preference.second
                        }
                    )

                    _uiState.value = UiState.Success(user)
                } else {
                    _uiState.value = UiState.Error("Failed to get user data")
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}