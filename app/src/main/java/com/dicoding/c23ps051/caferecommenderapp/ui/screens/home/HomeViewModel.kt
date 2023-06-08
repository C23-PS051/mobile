package com.dicoding.c23ps051.caferecommenderapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.c23ps051.caferecommenderapp.data.CafeRepository
import com.dicoding.c23ps051.caferecommenderapp.model.Cafe
import com.dicoding.c23ps051.caferecommenderapp.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CafeRepository) : ViewModel() {

    private val _uiStateNearby: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateNearby: StateFlow<UiState<List<Cafe>>> get() = _uiStateNearby

    private val _uiStateOpen24Hours: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOpen24Hours: StateFlow<UiState<List<Cafe>>> get() = _uiStateOpen24Hours

    private val _uiStateOnBudget: MutableStateFlow<UiState<List<Cafe>>> = MutableStateFlow(UiState.Loading)
    val uiStateOnBudget: StateFlow<UiState<List<Cafe>>> get() = _uiStateOnBudget

    fun getNearbyCafes() {
        viewModelScope.launch {
            // TODO: TO BE UPDATED (repository get function)
            repository.getNearbyCafes()
                .catch {
                    _uiStateNearby.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateNearby.value = UiState.Success(cafes)
                }
        }
    }

    fun getOpen24HoursCafes() {
        // TODO: TO BE UPDATED (repository get function)
        viewModelScope.launch {
            repository.getOpen24HoursCafes()
                .catch {
                    _uiStateOpen24Hours.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateOpen24Hours.value = UiState.Success(cafes)
                }
        }
    }

    fun getOnBudgetCafes() {
        // TODO: TO BE UPDATED (repository get function)
        viewModelScope.launch {
            repository.getOnBudgetCafes()
                .catch {
                    _uiStateOnBudget.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    _uiStateOnBudget.value = UiState.Success(cafes)
                }
        }
    }
}