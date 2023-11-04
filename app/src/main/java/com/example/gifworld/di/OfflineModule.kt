package com.example.gifworld.di

import android.content.Context
import com.example.gifworld.data.GifsDao
import com.example.gifworld.data.GifsDatabase
import com.example.gifworld.repository.GifOfflineRepository
import com.example.gifworld.repository.OfflineGifRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OfflineModule {
    @Singleton
    @Provides
    fun providesGifsDao(context: Context): GifsDao =
        GifsDatabase.getDatabase(context).gifsDao()

    @Singleton
    @Provides
    fun providesGifOfflineRepository(gifsDao: GifsDao): GifOfflineRepository =
        OfflineGifRepository(gifsDao)
}