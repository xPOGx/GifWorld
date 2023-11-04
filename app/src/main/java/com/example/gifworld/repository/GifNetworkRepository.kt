package com.example.gifworld.repository

import com.example.gifworld.network.GifApiService
import com.example.gifworld.network.GifResult
import javax.inject.Inject

interface GifNetworkRepository {
    suspend fun getGifs(query: String, offset: Int): GifResult
}

class NetworkGifRepository @Inject constructor(
    private val gifApiService: GifApiService
) : GifNetworkRepository {
    override suspend fun getGifs(query: String, offset: Int): GifResult =
        gifApiService.getGifs(query = query, offset = offset)
}