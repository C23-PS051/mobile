package com.dicoding.c23ps051.caferecommenderapp.constants

const val MIN_PASSWORD_LENGTH = 8
const val UNKNOWN = "unknown"
const val FAILED = "failed"
const val NOT_GRANTED = "not_granted"

val REGIONS = listOf(
    "All",
    "Kota Jakarta Utara",
    "Kota Jakarta Selatan",
    "Kota Jakarta Barat",
    "Kota Jakarta Timur",
    "Kota Jakarta Pusat",
    "Kota Bandung" // TODO: For experiment purposes only, remember to remove when done
)

val FACILITIES = listOf(
    "Indoor",
    "Outdoor",
    "Wi-Fi",
    "Kid-friendly",
    "Pet-friendly",
    "Takeaway",
    "Smoking area",
    "Parking area",
    "Toilets",
    "Live music",
    "In mall",
    "VIP room",
    "Reservation",
    "Alcohol",
)

val RATINGS = listOf(
    "1.0",
    "2.0",
    "3.0",
    "4.0",
    "5.0"
)

val PRICE_RANGE = listOf(
    "$",
    "$$",
    "$$$"
)