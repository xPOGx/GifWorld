package com.example.gifworld.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GifsDao {
    @Query("SELECT * from gifs WHERE search_query = :query AND uuid NOT IN (SELECT id from ban WHERE search_query = :query)")
    fun getAllGifs(query: String): Flow<List<GifDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGif(gifDB: GifDB)

    @Query("SELECT * from ban WHERE search_query = :query")
    fun getAllBan(query: String): Flow<List<GifBan>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBan(gifBan: GifBan)
}