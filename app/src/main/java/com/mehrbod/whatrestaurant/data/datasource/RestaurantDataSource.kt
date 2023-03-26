package com.mehrbod.whatrestaurant.data.datasource

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("Restaurants")
    val restaurants: List<Restaurant>
)

data class Restaurant(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
)

interface RestaurantDataSource {
    suspend fun getRestaurants(postcode: String): RestaurantsResponse
}
