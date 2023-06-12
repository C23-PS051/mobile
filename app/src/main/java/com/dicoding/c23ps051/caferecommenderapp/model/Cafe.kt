package com.dicoding.c23ps051.caferecommenderapp.model

data class Cafe(
    val id: String,
    val address: String,
    val closingHour: Int,
    val description: String,
    val name: String,
    val openingHour: Int,
    val priceCategory: String,
    val rating: Double,
    val region: String,
    val review: String,
    val thumbnail: String,
    val facilities: List<Pair<Facility, Boolean>>,
)

enum class Facility(val displayName: String) {
    ALCOHOL("Alcohol"),
    IN_MALL("In Mall"),
    INDOOR("Indoor"),
    KID_FRIENDLY("Kid Friendly"),
    LIVE_MUSIC("Live Music"),
    OUTDOOR("Outdoor"),
    PARKING_AREA("Parking Area"),
    PET_FRIENDLY("Pet Friendly"),
    RESERVATION("Reservation"),
    SMOKING_AREA("Smoking Area"),
    TAKEAWAY("Takeaway"),
    TOILETS("Toilets"),
    VIP_ROOM("VIP Room"),
    WIFI("Wi-Fi"),
}
