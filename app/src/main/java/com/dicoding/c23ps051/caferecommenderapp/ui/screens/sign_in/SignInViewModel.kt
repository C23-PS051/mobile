package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import kotlinx.coroutines.launch

class SignInViewModel(private val pref: UserPreference) : ViewModel() {

    private lateinit var loginModel: Login

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> get() = _loginSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    fun signInWithEmail(email: String, password: String) {
//        /* TODO: TO BE UPDATED WHEN API IS READY */
//        if (email == "john_doe331@mail.com" && password == "password123") {
//            _loginSuccess.value = true
//
//            val loginData = Login("John Doe", "john_doe331@mail.com", "ABCDEFGH12345678", true)
//
//            viewModelScope.launch {
//                pref.saveLogin(loginData)
//            }
//        } else {
//            _loginSuccess.value = false
//        }
//    }

    fun signIn(name: String?, email: String, token: String, photoUrl: String) {
        val convName = name ?: ""
        val convPhotoUrl = if (photoUrl == "null") "" else photoUrl
        val loginData = Login(convName, email, token, convPhotoUrl, isLogin = true, isNewUser = true, userLocation = "South Jakarta")

        viewModelScope.launch {
            pref.saveLogin(loginData)
        }
    }

//    fun signInWithGoogle(name: String, email: String, token: String, photoUrl: Uri) {
//        val loginData = Login(name, email, token, true)
//
//        viewModelScope.launch {
//            pref.saveLogin(loginData)
//        }
//    }
}

