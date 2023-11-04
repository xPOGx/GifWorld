package com.example.gifworld

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gifworld.di.AppComponent
import com.example.gifworld.ui.screens.SharedViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            SharedViewModel(
                networkRepository = appComponent().getNetworkRepository(),
                offlineRepository = appComponent().getOfflineRepository()
            )
        }
    }
}

fun CreationExtras.appComponent(): AppComponent =
    (this[APPLICATION_KEY] as AppDefaultApplication).appComponent