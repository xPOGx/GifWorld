package com.example.gifworld.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

object ScreenManager {
    @Composable
    fun isLandscape(): Boolean =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
}