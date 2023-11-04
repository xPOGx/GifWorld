package com.example.gifworld.utils

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest

object CoilManager {
    @Volatile
    private var imageLoader: ImageLoader? = null
    fun getImageLoader(context: Context): ImageLoader =
        imageLoader ?: synchronized(this) {
            ImageLoader.Builder(context)
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .components { // Gif support
                    if (SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
                    else add(GifDecoder.Factory())
                }
                .respectCacheHeaders(false)
                .build()
                .also { imageLoader = it }
        }

    fun getImageRequest(context: Context, data: Any?): ImageRequest =
        ImageRequest.Builder(context)
            .data(data)
            .diskCacheKey(data.toString())
            .memoryCacheKey(data.toString())
            .crossfade(true)
            .placeholder(android.R.drawable.ic_menu_upload)
            .error(android.R.drawable.ic_menu_close_clear_cancel)
            .build()
}