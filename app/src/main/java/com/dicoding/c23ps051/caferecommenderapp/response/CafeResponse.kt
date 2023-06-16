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

data class FavoriteCafeResponse(

	@field:SerializedName("result")
	val result: List<CafeResponseItem>,

	@field:SerializedName("status")
	val status: Int
)

data class CafeResponseItem(

	@field:SerializedName("cafe_id")
	val cafe_id: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("alcohol")
	val alcohol: Boolean,

	@field:SerializedName("closing_hour")
	val closing_hour: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("in_mall")
	val in_mall: Boolean,

	@field:SerializedName("indoor")
	val indoor: Boolean,

	@field:SerializedName("kid_friendly")
	val kid_friendly: Boolean,

	@field:SerializedName("live_music")
	val live_music: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("opening_hour")
	val opening_hour: Int,

	@field:SerializedName("outdoor")
	val outdoor: Boolean,

	@field:SerializedName("parking_area")
	val parking_area: Boolean,

	@field:SerializedName("pet_friendly")
	val pet_friendly: Boolean,

	@field:SerializedName("price_category")
	val price_category: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("region")
	val region: String,

	@field:SerializedName("reservation")
	val reservation: Boolean,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("smoking_area")
	val smoking_area: Boolean,

	@field:SerializedName("takeaway")
	val takeaway: Boolean,

	@field:SerializedName("toilets")
	val toilets: Boolean,

	@field:SerializedName("thumbnail_url")
	val thumbnail_url: String,

	@field:SerializedName("vip_room")
	val vip_room: Boolean,

	@field:SerializedName("wifi")
	val wifi: Int,
)
