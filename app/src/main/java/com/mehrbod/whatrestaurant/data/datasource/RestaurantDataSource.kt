package com.mehrbod.whatrestaurant.data.datasource

import com.mehrbod.whatrestaurant.data.datasource.model.RestaurantsResponse


/**
 * This interface is unnecessary for this project, but it's here to demonstrate the need for
 * an abstraction layer to have different data source, and easily implement other functionalities
 * related to them, such as offline first, cache, local storage, etc.
 */
interface RestaurantDataSource {
    suspend fun getRestaurants(postcode: String): RestaurantsResponse
}
