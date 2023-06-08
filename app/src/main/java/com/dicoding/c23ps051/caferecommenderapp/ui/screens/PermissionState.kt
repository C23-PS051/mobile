package com.dicoding.c23ps051.caferecommenderapp.ui.screens

sealed class PermissionState {

    object Initial : PermissionState()

    data class Granted(val location: String) : PermissionState()

    object NotGranted : PermissionState()

    data class Failed(val errorMessage: String) : PermissionState()
}