package com.dicoding.c23ps051.caferecommenderapp.model

data class Login(
    val name: String,
    val email: String,
    val token: String,
    val photoUrl: String,
    val isLogin: Boolean,
    val isNewUser: Boolean,
)