package com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.LoginModel
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import kotlinx.coroutines.launch

class SignInViewModel(private val pref: UserPreference) : ViewModel() {

    private lateinit var loginModel: LoginModel

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> get() = _loginSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        /* TODO: TO BE UPDATED WHEN API IS READY */
        if (email == "yen@mail.com" && password == "yendistia") {
            _loginSuccess.value = true

            val loginData = LoginModel("Yen", "yen@mail.com", "ABCDEFGH12345678", true)

            viewModelScope.launch {
                pref.saveLogin(loginData)
            }
        } else {
            _loginSuccess.value = false
        }
    }
}

