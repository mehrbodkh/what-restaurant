package com.mehrbod.whatrestaurant.data.datasource

import com.mehrbod.whatrestaurant.data.datasource.model.RestaurantsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteRestaurantDataSource @Inject constructor(
    private val client: HttpClient,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : RestaurantDataSource {
    override suspend fun getRestaurants(postcode: String): RestaurantsResponse =
        withContext(coroutineContext) {
            client.get("https://$HOST/$PATH/$postcode").body()
        }

    companion object {
        private const val HOST = "uk.api.just-eat.io/"
        private const val PATH = "restaurants/bypostcode"
    }
}
