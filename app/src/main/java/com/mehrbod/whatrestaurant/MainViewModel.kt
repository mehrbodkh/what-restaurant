package com.mehrbod.whatrestaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehrbod.whatrestaurant.data.datasource.RestaurantsResponse
import com.mehrbod.whatrestaurant.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val restaurants: RestaurantsResponse? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    val state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.update {
                it.copy(restaurants = repository.getRestaurants("ec4m"))
            }
        }
    }
}
