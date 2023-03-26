package com.mehrbod.whatrestaurant.domain

import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import com.mehrbod.whatrestaurant.data.repository.RestaurantRepository
import javax.inject.Inject

/**
 * In this case, this use case is not needed, as we can easily access the needed data from
 * repository directly, and domain layer is an optional layer from Google's suggested clean
 * architecture.
 * This use case is only here to demonstrate a complete layer of clean architecture and how
 * it can be used.
 */
class RestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(postcode: String): Response =
        repository.getRestaurants(postcode).toResponse()

    /**
     * This extra response is here to demonstrate the transformation of data between different
     * layers, however, it's not needed in this case.
     */
    sealed interface Response {
        data class Success(val restaurants: List<Restaurant>) : Response
        data class Failure(val message: String) : Response
    }

    private fun RestaurantRepository.Response.toResponse() = when (this) {
        is RestaurantRepository.Response.Success -> Response.Success(this.restaurants)
        is RestaurantRepository.Response.Failure -> Response.Failure(this.errorMessage)
    }
}
