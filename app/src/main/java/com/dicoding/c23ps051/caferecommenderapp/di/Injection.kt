package com.dicoding.c23ps051.caferecommenderapp.di

import com.dicoding.c23ps051.caferecommenderapp.api.ApiConfig
import com.dicoding.c23ps051.caferecommenderapp.api.ApiService
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository

object Injection {
    fun provideRepository(): CafeRepository {
        return CafeRepository.getInstance()
    }

    fun provideApi(): ApiService {
        return ApiConfig.getApiService()
    }
}