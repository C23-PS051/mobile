package com.dicoding.c23ps051.caferecommenderapp.api

import com.dicoding.c23ps051.caferecommenderapp.model.Favorite
import com.dicoding.c23ps051.caferecommenderapp.model.User
import com.dicoding.c23ps051.caferecommenderapp.response.CafeDetailResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponse
import com.dicoding.c23ps051.caferecommenderapp.response.CafeResponseItem
import com.dicoding.c23ps051.caferecommenderapp.response.Response
import com.dicoding.c23ps051.caferecommenderapp.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("users/{user-id}")
    suspend fun editProfile(
        @Header("Authorization") idToken: String,
        @Path("user-id") userId: String,
        @Body user: User,
    ): Response

    @GET("users/{user-id}")
    suspend fun getUserProfile(
        @Header("Authorization") idToken: String,
        @Path("user-id") userId: String,
    ): UserResponse

    @GET("ml/recommend")
    suspend fun getRecommendedCafes(
        @Header("Authorization") idToken: String,
    ): CafeResponse

    @POST("/favorites")
    suspend fun addToFavorite(
        @Header("Authorization") idToken: String,
        @Body favorite: Favorite
    ): Response

    @DELETE("/favorites")
    suspend fun removeFromFavorite(
        @Header("Authorization") idToken: String,
        @Body favorite: Favorite
    ): Response

    @GET("/favorites")
    suspend fun getFavoritesByUserId(
        @Header("Authorization") idToken: String,
        @Body userId: String,
    ): CafeResponse
}