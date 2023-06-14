package com.dicoding.c23ps051.caferecommenderapp.ui.screens.edit_profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>> get() = _uiState

    private val _resultState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Initial)
    val resultState: StateFlow<ResultState> get() = _resultState

    private lateinit var user: User

    private val _nameState = mutableStateOf("")
    val nameState get() = _nameState

    private val _photoUriState = mutableStateOf("")
    val photoUriState get() = _photoUriState

    fun getUserProfile() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                pref.getLogin().collect {
                    val response = ApiConfig.getApiService().getUserProfile(it.token, it.userId)

                    if (response.status == 200) {
                        val data = response.data

                        user = User(
                            fullName = data.fullName,
                            email = data.email,
                            photoUri = data.photoUri,
                            preferences = data.preferences.map { preference ->
                                Facility.valueOf(preference.first) to preference.second
                            },
                            username = data.username
                        )

                        _nameState.value = user.fullName
                        _photoUriState.value = user.photoUri

                        _uiState.value = UiState.Success(user)
                    } else {
                        _uiState.value = UiState.Error("Failed to get user data")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun editUserProfile() {
        _resultState.value = ResultState.Loading

        val newUserData = user.copy(fullName = nameState.value, photoUri = photoUriState.value)

        viewModelScope.launch {
            try {
                val loginData = pref.getLogin().first()
                val result = ApiConfig.getApiService().editProfile(loginData.token, loginData.userId, newUserData)
                if (result.status == 200) {
                    val firebaseUser = FirebaseAuth.getInstance().currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newUserData.fullName)
                        .setPhotoUri(Uri.parse(newUserData.photoUri))
                        .build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnSuccessListener {
                            viewModelScope.launch {
                                pref.editUser(newUserData.fullName, newUserData.photoUri)
                                _resultState.value = ResultState.Success
                            }
                        }
                        ?.addOnFailureListener {
                            _resultState.value = ResultState.Error(it.message.toString())
                        }
                } else {
                    _resultState.value = ResultState.Error("Failed to save changes")
                }
            } catch (e: Exception) {
                _resultState.value = ResultState.Error(e.message.toString())
            }
        }
    }
}