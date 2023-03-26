package com.mehrbod.whatrestaurant.data.repository.di

import com.mehrbod.whatrestaurant.data.datasource.RemoteRestaurantDataSource
import com.mehrbod.whatrestaurant.data.datasource.RestaurantDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsRemoteDataSource(
        dataSource: RemoteRestaurantDataSource
    ): RestaurantDataSource
}
