package com.example.gifworld.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.gifworld.AppDefaultApplication
import com.example.gifworld.ui.GifWorldApp
import com.example.gifworld.ui.theme.GifWorldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppDefaultApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GifWorldTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GifWorldApp()
                }
            }
        }
    }

    fun hideSystemUI() {
        actionBar?.hide()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        else
            window.insetsController?.apply {
                hide(WindowInsets.Type.systemBars())
            }
    }

    fun showSystemUi() {
        actionBar?.show()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            window.addFlags(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW)
        else
            window.insetsController?.apply {
                show(WindowInsets.Type.systemBars())
            }
    }
}