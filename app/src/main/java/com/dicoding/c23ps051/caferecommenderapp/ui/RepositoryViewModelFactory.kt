package com.dicoding.c23ps051.caferecommenderapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail.DetailViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.home.HomeViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended.RecommendedViewModel

class RepositoryViewModelFactory(private val repository: CafeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(RecommendedViewModel::class.java)) {
            return RecommendedViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}