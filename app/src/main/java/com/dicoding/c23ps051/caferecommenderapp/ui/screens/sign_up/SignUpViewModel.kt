package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val pref: UserPreference) : ViewModel() {

    private val auth = Firebase.auth

    private val _resultState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Initial)
    val resultState: StateFlow<ResultState> get() = _resultState

    fun signUpWithFirebaseAuth(
        email: String, password: String, name: String, photoUri: String
    ) {
        _resultState.value = ResultState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val firebaseUser = authTask.result.user

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .setPhotoUri(Uri.parse(photoUri))
                        .build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnSuccessListener {
                            firebaseUser.getIdToken(true)
                                .addOnSuccessListener { tokenResult ->
                                    if (tokenResult.token != null) {
                                        val idToken = tokenResult.token as String
                                        signUp(
                                            idToken = idToken,
                                            userId = firebaseUser.uid,
                                        )
                                    } else {
                                        _resultState.value =
                                            ResultState.Error("Failed to create user account")
                                    }
                                }.addOnFailureListener {
                                    _resultState.value = ResultState.Error(it.message.toString())
                                }
                        }?.addOnFailureListener {
                            _resultState.value = ResultState.Error(it.message.toString())
                        }
                } else {
                    _resultState.value = ResultState.Error("Failed to create user account")
                }
            }
    }

    private fun signUp(
        idToken: String, userId: String
    ) {
        viewModelScope.launch {
            val user = User(
                cafe_alcohol = false,
                cafe_in_mall = false,
                cafe_indoor = false,
                cafe_kid_friendly = false,
                cafe_live_music = false,
                cafe_outdoor = false,
                cafe_parking_area = false,
                cafe_pet_friendly = false,
                cafe_price_category = "",
                cafe_reservation = false,
                cafe_smoking_area = false,
                cafe_takeaway = false,
                cafe_toilets = false,
                cafe_vip_room = false,
                cafe_wifi = false,
                username = userId,
            )

            val response = ApiConfig.getApiService().editProfile(
                idToken = idToken,
                userId = userId,
                user = user,
            )

            if (response.status == 200) {
                _resultState.value = ResultState.Success
            } else {
                _resultState.value = ResultState.Error("Failed to create user account")
            }
        }
    }

    fun clearErrorState() {
        _resultState.value = ResultState.Initial
    }
}