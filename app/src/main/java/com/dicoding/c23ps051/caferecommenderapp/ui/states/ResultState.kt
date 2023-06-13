package com.dicoding.c23ps051.caferecommenderapp.ui.states

sealed class ResultState {

    object Initial : ResultState()
    object Loading : ResultState()
    object Success : ResultState()
    data class Error(val errorMessage: String) : ResultState()
}
