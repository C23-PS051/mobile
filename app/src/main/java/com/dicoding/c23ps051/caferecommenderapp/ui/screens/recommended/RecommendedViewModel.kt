package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RecommendedViewModel(private val repository: CafeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cafe>>> get() = _uiState

    fun getAllRecommendedCafes() {
        // TODO: TO BE UPDATED
        viewModelScope.launch {
            repository.getAllCafes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiState.value = UiState.Success(cafes)
                }
        }
    }
}