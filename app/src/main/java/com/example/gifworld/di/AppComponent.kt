package com.example.gifworld.di

import android.content.Context
import com.example.gifworld.activity.MainActivity
import com.example.gifworld.repository.GifNetworkRepository
import com.example.gifworld.repository.GifOfflineRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, OfflineModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getNetworkRepository(): GifNetworkRepository
    fun getOfflineRepository(): GifOfflineRepository

    fun inject(activity: MainActivity)
}