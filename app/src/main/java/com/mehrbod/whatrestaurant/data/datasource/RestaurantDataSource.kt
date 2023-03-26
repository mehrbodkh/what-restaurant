package com.mehrbod.whatrestaurant.data.datasource

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    val restaurants: Restaurant
)

data class Restaurant(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
)

interface RestaurantDataSource {
    suspend fun getRestaurants(): RestaurantsResponse
}
