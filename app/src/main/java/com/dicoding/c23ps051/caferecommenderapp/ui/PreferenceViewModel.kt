package com.dicoding.c23ps051.caferecommenderapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Login>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Login>> get() = _uiState

    fun getLoginAsLiveData(): LiveData<Login> {
        return pref.getLogin().asLiveData()
    }

    fun getLogin() {
        viewModelScope.launch {
            pref.getLogin()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    val loginModel = Login(
                        name = data.name,
                        email = data.email,
                        token = data.token,
                        photoUrl = data.photoUrl,
                        isLogin = data.isLogin
                    )
                    _uiState.value = UiState.Success(loginModel)
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}