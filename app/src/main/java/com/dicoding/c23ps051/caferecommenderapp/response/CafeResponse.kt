package com.dicoding.c23ps051.caferecommenderapp.response

import com.google.gson.annotations.SerializedName

data class CafeResponse(

	@field:SerializedName("data")
	val data: List<CafeResponseItem>,

	@field:SerializedName("status")
	val status: Int
)

data class CafeDetailResponse(

	@field:SerializedName("data")
	val data: CafeResponseItem,

	@field:SerializedName("status")
	val status: Int
)

data class CafeResponseItem(

	@field:SerializedName("alcohol")
	val alcohol: Boolean,

	@field:SerializedName("cafe_id")
	val cafeId: String,

	@field:SerializedName("opening_hour")
	val openingHour: Int,

	@field:SerializedName("pet_friendly")
	val petFriendly: Boolean,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String,

	@field:SerializedName("live_music")
	val liveMusic: Boolean,

	@field:SerializedName("kid_friendly")
	val kidFriendly: Boolean,

	@field:SerializedName("smoking_area")
	val smokingArea: Boolean,

	@field:SerializedName("closing_hour")
	val closingHour: Int,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("parking_area")
	val parkingArea: Boolean,

	@field:SerializedName("reservation")
	val reservation: Boolean,

	@field:SerializedName("wifi")
	val wifi: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("price_category")
	val priceCategory: String,

	@field:SerializedName("takeaway")
	val takeaway: Boolean,

	@field:SerializedName("outdoor")
	val outdoor: Boolean,

	@field:SerializedName("toilets")
	val toilets: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("indoor")
	val indoor: Boolean,

	@field:SerializedName("vip_room")
	val vipRoom: Boolean,

	@field:SerializedName("region")
	val region: String,

	@field:SerializedName("in_mall")
	val inMall: Boolean
)
