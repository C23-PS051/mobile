package com.dicoding.c23ps051.caferecommenderapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Login>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Login>> get() = _uiState

    fun getLoginAsLiveData() : LiveData<Login> {
        return pref.getLogin().asLiveData()
    }

    fun setNotNewUser() {
        viewModelScope.launch {
            pref.setNotNewUser()
        }
    }
}