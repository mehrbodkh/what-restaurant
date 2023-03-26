package com.mehrbod.whatrestaurant.data.repository

import com.google.common.truth.Truth.assertThat
import com.mehrbod.whatrestaurant.data.datasource.RestaurantDataSource
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class RestaurantRepositoryTest {
    @MockK
    lateinit var dataSource: RestaurantDataSource

    @InjectMockKs
    lateinit var repository: RestaurantRepository

    @Test
    fun `should return internal server error type`() = runTest {
        coEvery { dataSource.getRestaurants(any()) } throws mockk<ServerResponseException>(relaxed = true) {
            every { response.status } returns HttpStatusCode.InternalServerError
            every { message } returns "Test error message"
        }

        val result = repository.getRestaurants("test-postcode")

        assertThat(result).isEqualTo(RestaurantRepository.Response.Failure.InternalServerError("Test error message"))
    }

    @Test
    fun `should return bad request type`() = runTest {
        coEvery { dataSource.getRestaurants(any()) } throws mockk<ServerResponseException>(relaxed = true) {
            every { response.status } returns HttpStatusCode.BadRequest
            every { message } returns "Test error message"
        }

        val result = repository.getRestaurants("test-postcode")

        assertThat(result).isEqualTo(RestaurantRepository.Response.Failure.InvalidRequest("Test error message"))
    }

    @Test
    fun `should return general error type`() = runTest {
        coEvery { dataSource.getRestaurants(any()) } throws mockk<ServerResponseException>(relaxed = true) {
            every { message } returns "Test error message"
        }

        val result = repository.getRestaurants("test-postcode")

        assertThat(result).isEqualTo(RestaurantRepository.Response.Failure.Error("Test error message"))
    }

    @Test
    fun `should return correct answer`() = runTest {
        val restaurants = listOf(mockk<Restaurant>(relaxed = true))
        coEvery { dataSource.getRestaurants(any()) } returns mockk {
            every { this@mockk.restaurants } returns restaurants
        }

        val result = repository.getRestaurants("test-postcode")

        assertThat(result).isEqualTo(RestaurantRepository.Response.Success(restaurants))
    }
}
