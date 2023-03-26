package com.mehrbod.whatrestaurant.data.datasource

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mehrbod.whatrestaurant.data.datasource.model.RestaurantsResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.gson.gson
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class RemoteRestaurantDataSourceTest {

    companion object {
        const val TEST_POSTCODE = "test-postcode"
    }

    @Test
    fun `should throw exception if api call is unsuccessful`() = runTest {
        val engine = MockEngine {
            respondError(status = HttpStatusCode.InternalServerError)
        }
        val dataSource = RemoteRestaurantDataSource(HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation) {
                gson()
            }
        }, UnconfinedTestDispatcher())

        assertThrows<ServerResponseException> {
            dataSource.getRestaurants(TEST_POSTCODE)
        }
    }

    @Test
    fun `should return correct response model`() = runTest {
        val restaurants = mockk<RestaurantsResponse>(relaxed = true) {
            every { restaurants } returns listOf(mockk(relaxed = true), mockk(relaxed = true))
        }
        val engine = MockEngine {
            respond(
                content = Gson().toJson(restaurants),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dataSource = RemoteRestaurantDataSource(HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation) {
                gson()
            }
        }, UnconfinedTestDispatcher())

        val response = dataSource.getRestaurants(TEST_POSTCODE)
        assertThat(response).isEqualTo(restaurants)
    }
}
