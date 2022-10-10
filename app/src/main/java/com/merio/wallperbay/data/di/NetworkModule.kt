package com.merio.wallperbay.data.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.merio.wallperbay.R
import com.merio.wallperbay.data.network.interceptor.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val OK_HTTP_CACHE_SIZE = 10L * 1024L * 1024L

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule() {

    @Provides
    internal fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    internal fun providesCache(context: Application): Cache {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory,
            OK_HTTP_CACHE_SIZE
        )
    }

    @Provides
    internal fun providesOkHttpClient(cache: Cache, context: Application): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(logging)
            addNetworkInterceptor(
                RequestInterceptor(
                    context.getString(R.string.apikey)
                )
            )
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }
        return httpClient.build()
    }

    @Provides
    internal fun providesRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        context: Application
    ): Retrofit {
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(context.getString(R.string.server_address))
            .client(okHttpClient)

        return builder.build()
    }
}

