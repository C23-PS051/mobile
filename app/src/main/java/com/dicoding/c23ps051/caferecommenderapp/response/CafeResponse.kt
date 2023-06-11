package com.dicoding.c23ps051.caferecommenderapp.response

import com.google.gson.annotations.SerializedName

data class CafeResponse(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: List<CafeResponseItem>,
)

data class CafeResponseItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("detail")
    val detail: List<CafeDetailResponse>,
)

data class CafeDetailResponse(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("closing_hour")
    val closingHour: Int,

    @field: SerializedName("description")
    val description: String,

    @field: SerializedName("name")
    val name: String,

    @field:SerializedName("opening_hour")
    val openingHour: Int,

    @field:SerializedName("price_category")
    val priceCategory: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("region")
    val region: String,

    @field:SerializedName("review")
    val review: Int,

    @field:SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @field:SerializedName("alcohol")
    val alcohol: Boolean,

    @field:SerializedName("indoor")
    val indoor: Boolean,

    @field:SerializedName("in_mall")
    val inMall: Boolean,

    @field:SerializedName("kid_friendly")
    val kidFriendly: Boolean,

    @field:SerializedName("live_music")
    val liveMusic: Boolean,

    @field:SerializedName("outdoor")
    val outdoor: Boolean,

    @field:SerializedName("parking_area")
    val parkingArea: Boolean,

    @field:SerializedName("pet_friendly")
    val petFriendly: Boolean,

    @field:SerializedName("reservation")
    val reservation: Boolean,

    @field:SerializedName("smoking_area")
    val smokingArea: Boolean,

    @field:SerializedName("takeaway")
    val takeaway: Boolean,

    @field:SerializedName("toilets")
    val toilets: Boolean,

    @field:SerializedName("vip_room")
    val vipRoom: Boolean,

    @field:SerializedName("wifi")
    val wifi: Boolean,
)