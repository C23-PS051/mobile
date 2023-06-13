package com.dicoding.c23ps051.caferecommenderapp.model

data class User (
    val email: String,
    val fullName: String,
    val photoUri: String,
    val preferences: List<Pair<Facility, Boolean>>,
)