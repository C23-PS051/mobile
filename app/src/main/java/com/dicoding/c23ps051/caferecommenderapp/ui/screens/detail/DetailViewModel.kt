package com.dicoding.c23ps051.caferecommenderapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CafeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Cafe>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cafe>> get() = _uiState

    fun getCafeById(id: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCafeById(id))
        }
    }
}