package com.mehrbod.whatrestaurant.data.repository

import com.mehrbod.whatrestaurant.data.datasource.RestaurantDataSource
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val dataSource: RestaurantDataSource
) {

    suspend fun getRestaurants() = dataSource.getRestaurants()

}
