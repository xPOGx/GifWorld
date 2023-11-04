package com.example.gifworld.di

import com.example.gifworld.network.GifApiService
import com.example.gifworld.repository.GifNetworkRepository
import com.example.gifworld.repository.NetworkGifRepository
import com.example.gifworld.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesGifApi(retrofit: Retrofit): GifApiService {
        return retrofit.create(GifApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesNetworkRepository(apiService: GifApiService): GifNetworkRepository {
        return NetworkGifRepository(apiService)
    }
}