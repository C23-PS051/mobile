package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.constants.DEFAULT_PHOTO_URI
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.Login
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
                                            email = email,
                                            password = password,
                                            name = name,
                                            photoUri = photoUri
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
        idToken: String, userId: String, email: String, password: String, name: String, photoUri: String
    ) {
        viewModelScope.launch {
            val user = User(
                email = email,
                fullName = name.trim(),
                photoUri = photoUri,
                preferences = enumValues<Facility>().map { it to false },
                username = userId
            )

            val response = ApiConfig.getApiService().editProfile(
                idToken = idToken,
                userId = userId,
                user = user,
            )

            if (response.status == 200) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser

                            currentUser?.getIdToken(true)
                                ?.addOnCompleteListener { tokenTask ->
                                    if (tokenTask.isSuccessful) {
                                        val token = tokenTask.result?.token
                                        if (token != null) {
                                            signIn(
                                                name = name,
                                                email = email,
                                                token = token,
                                                photoUri = photoUri,
                                                userId = userId,
                                            )
                                        }
                                    } else {
                                        _resultState.value = ResultState.Error("Failed to create user account")
                                    }
                                }?.addOnFailureListener {
                                    _resultState.value = ResultState.Error(it.message.toString())
                                }
                        } else {
                            _resultState.value = ResultState.Error("Failed to create user account")
                        }
                    }.addOnFailureListener {
                        _resultState.value = ResultState.Error(it.message.toString())
                    }
            } else {
                _resultState.value = ResultState.Error("Failed to create user account")
            }
        }
    }

    private fun signIn(name: String?, email: String, token: String, photoUri: String, userId: String) {
        Log.d("MyLogger", "Sign Up: $photoUri")
        val convName = name ?: ""
        val convPhotoUrl = if (photoUri == "null") DEFAULT_PHOTO_URI else photoUri
        val loginData = Login(
            name = convName,
            email = email,
            photoUrl = convPhotoUrl,
            token = token,
            isLogin = true,
            isNewUser = true,
            userId = userId,
        )

        viewModelScope.launch {
            pref.saveLogin(loginData)
        }
    }

    fun clearErrorState() {
        _resultState.value = ResultState.Initial
    }
}