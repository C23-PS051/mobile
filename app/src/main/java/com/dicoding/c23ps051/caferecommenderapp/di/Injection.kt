package com.dicoding.c23ps051.caferecommenderapp.di

import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository

object Injection {
    fun provideRepository(): CafeRepository {
        return CafeRepository.getInstance()
    }
}