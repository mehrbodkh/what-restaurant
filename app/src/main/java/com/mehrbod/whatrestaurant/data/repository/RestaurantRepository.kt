package com.mehrbod.whatrestaurant.data.repository

import com.mehrbod.whatrestaurant.data.datasource.RestaurantDataSource
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val dataSource: RestaurantDataSource
) {

    suspend fun getRestaurants(postcode: String): Response {
        return try {
            Response.Success(
                dataSource.getRestaurants(postcode).restaurants.filter { it.isOpenNow }
            )
        } catch (e: ServerResponseException) {
            e.toFailure()
        }
    }

    sealed interface Response {
        data class Success(val restaurants: List<Restaurant>) : Response

        sealed class Failure(val errorMessage: String) : Response {
            data class InternalServerError(val message: String) : Failure(message)
            data class InvalidRequest(val message: String) : Failure(message)
            data class Error(val message: String) : Failure(message)
        }
    }

    private fun ServerResponseException.toFailure(): Response.Failure =
        when (this.response.status) {
            HttpStatusCode.InternalServerError -> Response.Failure.InternalServerError(this.message)
            HttpStatusCode.BadRequest -> Response.Failure.InvalidRequest(this.message)
            else -> Response.Failure.Error(this.message)
        }
}
