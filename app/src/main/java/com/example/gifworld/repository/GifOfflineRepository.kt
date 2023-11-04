package com.example.gifworld.repository

import com.example.gifworld.data.GifBan
import com.example.gifworld.data.GifDB
import com.example.gifworld.data.GifsDao
import com.example.gifworld.data.toGifData
import com.example.gifworld.network.GifData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GifOfflineRepository {
    fun getAllGifsStream(query: String): Flow<List<GifData>>
    suspend fun insertGif(gif: GifDB)
    fun getAllBanStream(query: String): Flow<List<GifBan>>
    suspend fun insertBan(ban: GifBan)
}

class OfflineGifRepository @Inject constructor(
    private val gifsDao: GifsDao
) : GifOfflineRepository {
    override fun getAllGifsStream(query: String): Flow<List<GifData>> =
        gifsDao.getAllGifs(query).map { list ->
            list.map { gif ->
                gif.toGifData()
            }
        }

    override suspend fun insertGif(gif: GifDB) = gifsDao.insertGif(gif)
    override fun getAllBanStream(query: String): Flow<List<GifBan>> = gifsDao.getAllBan(query)
    override suspend fun insertBan(ban: GifBan) = gifsDao.insertBan(ban)
}