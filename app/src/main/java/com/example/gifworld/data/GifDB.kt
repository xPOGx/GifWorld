package com.example.gifworld.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gifworld.network.GifData
import com.example.gifworld.network.ImagesData
import com.example.gifworld.network.OriginalImage
import com.example.gifworld.network.PreviewImage

@Entity(tableName = "gifs")
data class GifDB(
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "search_query")
    val searchQuery: String,
    val title: String,
    val type: String,
    val original: String,
    val preview: String
)

@Entity(tableName = "ban")
data class GifBan(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "search_query")
    val searchQuery: String,
)

fun GifDB.toGifData(): GifData =
    GifData(
        type = type,
        id = uuid,
        title = title,
        images = ImagesData(
            PreviewImage(preview),
            OriginalImage(original)
        )
    )