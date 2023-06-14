package com.dicoding.c23ps051.caferecommenderapp.ui.states

sealed class PermissionState {

    object Initial : PermissionState()

    data class Granted(val data: String) : PermissionState()

    object NotGranted : PermissionState()

    data class Failed(val errorMessage: String) : PermissionState()
}