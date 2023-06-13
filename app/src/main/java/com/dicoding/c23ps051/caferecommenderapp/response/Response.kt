package com.dicoding.c23ps051.caferecommenderapp.response

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,
)