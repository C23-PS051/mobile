package com.dicoding.c23ps051.caferecommenderapp.ui.screens.recommended

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private var recommendedCafes = listOf<Cafe>()

    private var filteredRecommendedCafes = listOf<Cafe>()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllRecommendedCafes() {
        // TODO: TO BE UPDATED
        viewModelScope.launch {
            repository.getAllCafes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cafes ->
                    recommendedCafes = cafes
                    _uiState.value = UiState.Success(cafes)
                }
        }
    }

    fun searchCafes(query: String) {
        _query.value = query
        filteredRecommendedCafes = recommendedCafes.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _uiState.value = UiState.Success(filteredRecommendedCafes)
    }

    fun getNearestCafes() {
        _uiState.value = UiState.Success(
            filteredRecommendedCafes.sortedBy {
                it.distance
            }
        )
    }

    fun getHighestRatingCafes() {
        _uiState.value = UiState.Success(
            filteredRecommendedCafes.sortedByDescending {
                it.rating
            }
        )
    }

    fun getLowestPriceRangeCafes() {
        _uiState.value = UiState.Success(
            filteredRecommendedCafes.sortedBy {
                it.maxPrice - it.minPrice
            }
        )
    }

    fun getHighestPriceRangeCafes() {
        _uiState.value = UiState.Success(
            filteredRecommendedCafes.sortedByDescending {
                it.maxPrice - it.minPrice
            }
        )
    }
}