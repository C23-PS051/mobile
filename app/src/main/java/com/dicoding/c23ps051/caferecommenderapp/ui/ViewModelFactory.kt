package com.dicoding.c23ps051.caferecommenderapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.c23ps051.caferecommenderapp.model.UserPreference
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail.DetailViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.home.HomeViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.location.LocationViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.edit_profile.EditProfileViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.profile.ProfileViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended.RecommendedViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.search.SearchViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_in.SignInViewModel
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.sign_up.SignUpViewModel

class ViewModelFactory(private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PreferenceViewModel::class.java) -> {
                PreferenceViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LocationViewModel::class.java) -> {
                LocationViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RecommendedViewModel::class.java) -> {
                RecommendedViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}