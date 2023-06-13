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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Login>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Login>> get() = _uiState

    val location get() = pref.getUserLocation()

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
                        photoUrl = data.photoUrl,
                        token = data.token,
                        isLogin = data.isLogin,
                        isNewUser = data.isNewUser,
                        userId = data.userId,
                    )
                    _uiState.value = UiState.Success(loginModel)
                }
        }
    }

    fun setNotNewUser() {
        viewModelScope.launch {
            pref.setNotNewUser()
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}