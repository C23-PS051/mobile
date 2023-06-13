package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.model.Facility
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.response.UserResponse
import com.dicoding.c23ps051.caferecommenderapp.ui.states.ResultState
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val pref: UserPreference) : ViewModel() {

    private val auth = Firebase.auth

    private val _resultState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Initial)
    val resultState: StateFlow<ResultState> get() = _resultState

    fun signUpWithFirebaseAuth(
        username: String, email: String, password: String, name: String, photoUri: String
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
                                            idToken,
                                            firebaseUser.uid,
                                            username,
                                            email,
                                            password,
                                            name,
                                            photoUri
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
        idToken: String, userId: String, username: String, email: String, password: String, name: String, photoUri: String
    ) {
        viewModelScope.launch {
            val user = User(
                email = email,
                full_name = name,
                photoUri = photoUri,
                username = username,
                preferences = enumValues<Facility>().map { it to false }
            )

            val client = ApiConfig.getApiService().editProfile(
                idToken = idToken,
                userId = userId,
                user = user,
            )

            client.enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val currentUser = auth.currentUser
                                    val isNewUser = true

                                    currentUser?.getIdToken(true)
                                        ?.addOnCompleteListener { tokenTask ->
                                            if (tokenTask.isSuccessful) {
                                                val token = tokenTask.result?.token
                                                if (token != null) {
                                                    signIn(
                                                        name = name,
                                                        email = email,
                                                        token = token,
                                                        photoUrl = photoUri,
                                                        userId = userId,
                                                        isNewUser = isNewUser,
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

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _resultState.value = ResultState.Error(t.message.toString())
                }
            })
        }
    }

    fun signIn(name: String?, email: String, token: String, photoUrl: String, userId: String, isNewUser: Boolean) {
        val convName = name ?: ""
        val convPhotoUrl = if (photoUrl == "null") "" else photoUrl
        val loginData = Login(
            name = convName,
            email = email,
            photoUrl = convPhotoUrl,
            token = token,
            isLogin = true,
            isNewUser = isNewUser,
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