package com.merio.wallperbay.data.network.api

import com.merio.wallperbay.data.network.modules.ImageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WallperbayApiClient {

    @GET("api/")
    fun searchImage(
        @Query("q") q: String
    ): Single<ImageResponse>
}
