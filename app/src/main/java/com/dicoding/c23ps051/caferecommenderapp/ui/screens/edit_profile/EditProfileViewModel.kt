package com.dicoding.c23ps051.caferecommenderapp.ui.screens.edit_profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.model.UserSimple
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.dicoding.c23ps051.caferecommenderapp.ui.states.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UserSimple>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<UserSimple>> get() = _uiState

    private val _resultState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Initial)
    val resultState: StateFlow<ResultState> get() = _resultState

    private val _nameState = mutableStateOf("")
    val nameState get() = _nameState

    private val _photoUriState = mutableStateOf("")
    val photoUriState get() = _photoUriState

    fun getUserProfile() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser

                val user = UserSimple(
                    email = currentUser?.email as String,
                    fullName = currentUser.displayName as String,
                    photoUri = currentUser.photoUrl.toString()
                )

                _nameState.value = user.fullName
                _photoUriState.value = user.photoUri

                _uiState.value = UiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun editUserProfile() {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(_nameState.value)
                    .setPhotoUri(Uri.parse(_photoUriState.value))
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnSuccessListener {
                        viewModelScope.launch {
                            pref.editUser(_nameState.value, _photoUriState.value)
                            _resultState.value = ResultState.Success
                        }
                    }?.addOnFailureListener {
                        _resultState.value = ResultState.Error(it.message.toString())
                    }
            } catch (e: Exception) {
                _resultState.value = ResultState.Error(e.message.toString())
            }
        }
    }
}