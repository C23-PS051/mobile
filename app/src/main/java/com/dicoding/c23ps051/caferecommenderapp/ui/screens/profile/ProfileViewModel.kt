package com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.model.UserSimple
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UserSimple>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<UserSimple>> get() = _uiState

    fun getUserProfile() {
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val user = UserSimple(
                        fullName = it.name,
                        email = it.email,
                        photoUri = it.photoUrl,
                    )
                    _uiState.value = UiState.Success(user)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}