package com.merio.wallperbay.data.repository

import com.merio.wallperbay.data.network.api.WallperbayApiClient
import com.merio.wallperbay.data.network.modules.Image
import io.reactivex.Single
import javax.inject.Inject

class VisualParadiseRepository @Inject constructor(
    private val visualParadiseApiClient: WallperbayApiClient
) {

    fun getImages(q: String): Single<List<Image>> {
        return visualParadiseApiClient.searchImage(q)
            .map { response ->
                response.hits
            }
    }
}
