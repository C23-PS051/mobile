package com.dicoding.c23ps051.caferecommenderapp.api

import com.dicoding.c23ps051.caferecommenderapp.response.CafeDetailResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponseItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("cafes")
    suspend fun getAllCafes(
        @Header("Authorization") idToken: String
    ): CafeResponse

    @GET("cafes/{cafe-id}")
    suspend fun getCafeById(
        @Header("Authorization") idToken: String,
        @Path("cafe-id") cafeId: String,
    ): CafeDetailResponse
}