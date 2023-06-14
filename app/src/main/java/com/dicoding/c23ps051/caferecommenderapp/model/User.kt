package com.dicoding.c23ps051.caferecommenderapp.model

data class User(
    val email: String,
    val fullName: String,
    val isNewUser: Boolean,
    val photoUri: String,
    val preferences: List<Pair<Facility, Boolean>>,
    val username: String,
)

data class UserSimple(
    val email: String,
    val fullName: String,
    val photoUri: String,
)