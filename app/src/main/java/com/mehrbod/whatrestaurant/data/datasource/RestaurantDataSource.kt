package com.mehrbod.whatrestaurant.data.datasource

import com.mehrbod.whatrestaurant.data.datasource.model.RestaurantsResponse


interface RestaurantDataSource {
    suspend fun getRestaurants(postcode: String): RestaurantsResponse
}
