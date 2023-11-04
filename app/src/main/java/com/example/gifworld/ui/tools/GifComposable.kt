package com.example.gifworld.ui.tools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.gifworld.utils.CoilManager
import com.example.gifworld.utils.ScreenManager

@Composable
fun GifComposable(
    modifier: Modifier = Modifier,
    gifUrl: String,
    contentScale: ContentScale? = null,
    onClick: () -> Unit = {}
) {
    val scale = contentScale
        ?: if (ScreenManager.isLandscape()) ContentScale.FillHeight else ContentScale.FillWidth

    val context = LocalContext.current
    val imageRequest = CoilManager.getImageRequest(context, gifUrl)
    val imageLoader = CoilManager.getImageLoader(context)
    AsyncImage(
        model = imageRequest,
        imageLoader = imageLoader,
        contentDescription = null,
        contentScale = scale,
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = MutableInteractionSource(),
            indication = null
        )
    )
}