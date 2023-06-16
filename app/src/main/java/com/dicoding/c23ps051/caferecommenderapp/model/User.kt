package com.dicoding.c23ps051.caferecommenderapp.model

data class User(
    val cafe_alcohol: Boolean,
    val cafe_in_mall: Boolean,
    val cafe_indoor: Boolean,
    val cafe_kid_friendly: Boolean,
    val cafe_live_music: Boolean,
    val cafe_outdoor: Boolean,
    val cafe_parking_area: Boolean,
    val cafe_pet_friendly: Boolean,
    val cafe_price_category: String,
    val cafe_reservation: Boolean,
    val cafe_smoking_area: Boolean,
    val cafe_takeaway: Boolean,
    val cafe_toilets: Boolean,
    val cafe_vip_room: Boolean,
    val cafe_wifi: Boolean,
    val username: String,
)

data class UserSimple(
    val email: String,
    val fullName: String,
    val photoUri: String,
)