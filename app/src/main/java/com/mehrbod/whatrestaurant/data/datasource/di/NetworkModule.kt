package com.mehrbod.whatrestaurant.data.datasource.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpClient() = HttpClient(Android) {
        expectSuccess = true
        install(ContentNegotiation) {
            gson()
        }
    }

    @Provides
    fun provideIODispatcher(): CoroutineContext = Dispatchers.IO
}
