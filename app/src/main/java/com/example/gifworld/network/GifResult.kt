package com.example.gifworld.network

import com.example.gifworld.data.GifDB
import com.squareup.moshi.Json

data class GifResult(
    val data: List<GifData>,
    val pagination: PaginationData,
    val meta: MetaData
)

data class GifData(
    val type: String,
    val id: String,
    val title: String,
    val images: ImagesData
)

fun GifData.toGifDB(searchQuery: String): GifDB =
    GifDB(
        uuid = id,
        title = title,
        type = type,
        searchQuery = searchQuery,
        original = images.original.webp,
        preview = images.preview.url
    )

data class ImagesData(
    @Json(name = "preview_gif") val preview: PreviewImage,
    val original: OriginalImage
)

data class PreviewImage(
    val url: String
)

data class OriginalImage(
    val webp: String
)

data class PaginationData(
    val offset: Int,
    @Json(name = "total_count") val totalCount: Int,
    val count: Int
)

data class MetaData(
    @Json(name = "msg") val message: String,
    val status: Int
)
