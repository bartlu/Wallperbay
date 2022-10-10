package com.merio.wallperbay.data.di

import com.merio.wallperbay.data.network.api.WallperbayApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun providesVisualParadiseApiClient(retrofit: Retrofit) = retrofit.create(
        WallperbayApiClient::class.java)
}
