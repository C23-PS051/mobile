package com.dicoding.c23ps051.caferecommenderapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.model.LoginModel
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import kotlinx.coroutines.launch

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    fun getLogin(): LiveData<LoginModel> {
        return pref.getLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}