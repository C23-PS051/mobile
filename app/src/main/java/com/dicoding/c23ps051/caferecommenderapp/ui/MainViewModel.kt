package com.dicoding.c23ps051.caferecommenderapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Login>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Login>> get() = _uiState

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

    fun getLoginAsLiveData() : LiveData<Login> {
        return pref.getLogin().asLiveData()
    }

    fun setNotNewUser() {
        viewModelScope.launch {
            try {
                val userData = pref.getLogin().first()

                val response =
                    ApiConfig.getApiService().getUserProfile(userData.token, userData.userId)
                if (response.status == 200) {
                    val data = response.data
                    val user = User(
                        email = data.email,
                        fullName = data.fullName,
                        username = data.username,
                        isNewUser = false,
                        preferences = data.preferences.map { preference ->
                            Facility.valueOf(preference.first) to preference.second
                        },
                        photoUri = data.photoUri
                    )

                    val result = ApiConfig.getApiService().editProfile(userData.token, userData.userId, user)
                    if (result.status == 200) {
                        pref.setNotNewUser()
                    }
                }
            } catch (e: Exception) {
                Log.d("MyLogger", e.message.toString())
            }
        }
    }

//    suspend fun isNewUser(token: String, uid: String): Boolean {
//        return suspendCoroutine { continuation ->
//            viewModelScope.launch {
//                val response = ApiConfig.getApiService().getUserProfile(token, uid)
//                if (response.status == 200) {
//                    val data = response.data
//                    val filtered = data.preferences.filter { it.second }
//                    val result = filtered.isEmpty()
//                    continuation.resume(result)
//                } else {
//                    continuation.resume(false)
//                }
//            }
//        }
//    }

}