package com.dicoding.c23ps051.caferecommenderapp.api

import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("cafes")
    suspend fun getAllCafes(): Call<CafeResponse>
}