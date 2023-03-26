package com.mehrbod.whatrestaurant.domain

import com.google.common.truth.Truth.assertThat
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant
import com.mehrbod.whatrestaurant.data.repository.RestaurantRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class RestaurantsUseCaseTest {

    @RelaxedMockK
    lateinit var repository: RestaurantRepository

    @InjectMockKs
    lateinit var useCase: RestaurantsUseCase

    @Test
    fun `should call restaurants api from repository`() = runTest {
        useCase("test-postcode")
        coVerify { repository.getRestaurants("test-postcode") }
    }

    @Test
    fun `should return success if data fetching is successful`() = runTest {
        val restaurants = listOf(mockk<Restaurant>(relaxed = true), mockk(relaxed = true))
        coEvery { repository.getRestaurants(any()) } returns mockk<RestaurantRepository.Response.Success> {
            every { this@mockk.restaurants } returns restaurants
        }

        val result = useCase("test-postcode")

        assertThat(result).isEqualTo(RestaurantsUseCase.Response.Success(restaurants))
    }

    @Test
    fun `should return failure if data fetching is not successful`() = runTest {
        coEvery { repository.getRestaurants(any()) } returns mockk<RestaurantRepository.Response.Failure>(
            relaxed = true
        ) {
            every { this@mockk.errorMessage } returns "Test error message"
        }

        val result = useCase("test-postcode")

        assertThat(result).isEqualTo(RestaurantsUseCase.Response.Failure("Test error message"))
    }
}
