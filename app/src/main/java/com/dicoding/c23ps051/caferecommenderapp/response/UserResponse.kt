package com.dicoding.c23ps051.caferecommenderapp.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: UserResponseItem,

	@field:SerializedName("status")
	val status: Int
)

data class UserResponseItem(

	@field:SerializedName("preferences")
	val preferences: List<PreferencesItem>,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("photoUri")
	val photoUri: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("isNewUser")
	val isNewUser: Boolean
)

data class PreferencesItem(

	@field:SerializedName("first")
	val first: String,

	@field:SerializedName("second")
	val second: Boolean
)

//data class UserResponseItem(
//
//	@field:SerializedName("cafe_alcohol")
//	val cafeAlcohol: Boolean,
//
//	@field:SerializedName("cafe_in_mall")
//	val cafeInMall: Boolean,
//
//	@field:SerializedName("cafe_indoor")
//	val cafeIndoor: Boolean,
//
//	@field:SerializedName("cafe_kid_friendly")
//	val cafeKidFriendly: Boolean,
//
//	@field:SerializedName("cafe_live_music")
//	val cafeLiveMusic: Boolean,
//
//	@field:SerializedName("cafe_outdoor")
//	val cafeOutdoor: Boolean,
//
//	@field:SerializedName("cafe_parking_area")
//	val cafeParkingArea: Boolean,
//
//	@field:SerializedName("cafe_pet_friendly")
//	val cafePetFriendly: Boolean,
//
//	@field:SerializedName("cafe_price_category")
//	val cafePriceCategory: String,
//
//	@field:SerializedName("cafe_reservation")
//	val cafeReservation: Boolean,
//
//	@field:SerializedName("cafe_smoking_area")
//	val cafeSmokingArea: Boolean,
//
//	@field:SerializedName("cafe_takeaway")
//	val cafeTakeaway: Boolean,
//
//	@field:SerializedName("cafe_toilets")
//	val cafeToilets: Boolean,
//
//	@field:SerializedName("cafe_vip_room")
//	val cafeVipRoom: Boolean,
//
//	@field:SerializedName("cafe_wifi")
//	val cafeWifi: Boolean,
//
//	@field:SerializedName("email")
//	val email: String,
//
//	@field:SerializedName("fullName")
//	val fullName: String,
//
//	@field:SerializedName("photoUri")
//	val photoUri: String,
//
//	@field:SerializedName("username")
//	val username: String,
//)