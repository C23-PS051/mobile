package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.Login
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import kotlinx.coroutines.launch

class SignInViewModel(private val pref: UserPreference) : ViewModel() {

    fun signIn(name: String, email: String, token: String, photoUrl: String, userId: String, isNewUser: Boolean) {
        val convPhotoUrl = if (photoUrl == "null") "" else photoUrl
        val loginData = Login(
            name = name,
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
}

