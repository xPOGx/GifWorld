package com.example.gifworld

import android.app.Application
import com.example.gifworld.di.AppComponent
import com.example.gifworld.di.DaggerAppComponent

class AppDefaultApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}