package com.mehrbod.whatrestaurant.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRestaurantDataSource @Inject constructor(
    private val client: HttpClient
) : RestaurantDataSource {
    override suspend fun getRestaurants() = withContext(Dispatchers.IO) {
        client.get("https://uk.api.just-eat.io/restaurants/bypostcode/1053dh")
            .body<RestaurantsResponse>()
    }
}
