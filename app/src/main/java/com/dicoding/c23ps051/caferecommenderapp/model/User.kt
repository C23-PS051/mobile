package com.dicoding.c23ps051.caferecommenderapp.model

data class User (
    val email: String,
    val full_name: String,
    val username: String,
    val photoUri: String,
    val preferences: List<Pair<Facility, Boolean>>,
)