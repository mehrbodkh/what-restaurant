package com.mehrbod.whatrestaurant.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import com.mehrbod.whatrestaurant.domain.RestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState {
    object Idle : UiState
    object Loading : UiState
    data class Loaded(val restaurants: List<Restaurant>) : UiState
    data class Error(val message: String) : UiState
}

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val useCase: RestaurantsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updatePostcode(postcode: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = useCase(postcode)) {
                is RestaurantsUseCase.Response.Failure -> {
                    _uiState.value = UiState.Error(result.message)
                }

                is RestaurantsUseCase.Response.Success -> {
                    _uiState.value = UiState.Loaded(result.restaurants)
                }
            }
        }
    }
}
