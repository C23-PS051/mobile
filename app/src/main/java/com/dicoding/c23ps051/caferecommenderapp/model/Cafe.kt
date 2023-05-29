package com.dicoding.c23ps051.caferecommenderapp.model

data class Cafe(
    val id: Long,
    val thumbnail: String,
    val name: String,
    val address: String,
    val rating: Double,
    val ratingCount: Int,
    val distance: Double,
    val isOpen: Boolean,
    val minPrice: Long,
    val maxPrice: Long,
)
