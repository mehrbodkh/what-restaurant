package com.mehrbod.whatrestaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import com.mehrbod.whatrestaurant.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val restaurants: List<Restaurant>? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    val state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getRestaurants("ec4m")) {
                is RestaurantRepository.Response.Failure.Error -> {}
                is RestaurantRepository.Response.Failure.InternalServerError -> {}
                is RestaurantRepository.Response.Failure.InvalidRequest -> {}
                is RestaurantRepository.Response.Success -> state.update {
                    it.copy(restaurants = result.restaurants)
                }
            }
        }
    }
}
