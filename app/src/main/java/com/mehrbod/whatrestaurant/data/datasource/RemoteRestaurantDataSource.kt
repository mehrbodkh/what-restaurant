package com.mehrbod.whatrestaurant.data.datasource

import com.mehrbod.whatrestaurant.data.datasource.model.RestaurantsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRestaurantDataSource @Inject constructor(
    private val client: HttpClient
) : RestaurantDataSource {
    override suspend fun getRestaurants(postcode: String) = withContext(Dispatchers.IO) {
        client.get("https://$HOST/$PATH/$postcode")
            .body<RestaurantsResponse>()
    }

    companion object {
        private const val HOST = "uk.api.just-eat.io/"
        private const val PATH = "restaurants/bypostcode"
    }
}
