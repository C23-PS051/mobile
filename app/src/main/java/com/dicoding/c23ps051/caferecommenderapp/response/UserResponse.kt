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
	val username: String
)

data class PreferencesItem(

	@field:SerializedName("first")
	val first: String,

	@field:SerializedName("second")
	val second: Boolean
)
