package com.example.gifworld.network

import com.example.gifworld.utils.Constants.API_KEY
import com.example.gifworld.utils.Constants.BASE_LIMIT
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApiService {
    @GET("gifs/search")
    suspend fun getGifs(
        @Query(value = "api_key") apiKey: String = API_KEY,
        @Query(value = "q") query: String,
        @Query(value = "limit") limit: Int = BASE_LIMIT,
        @Query(value = "offset") offset: Int
    ): GifResult
}