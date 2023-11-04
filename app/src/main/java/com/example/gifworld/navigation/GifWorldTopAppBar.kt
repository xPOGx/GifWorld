package com.example.gifworld.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gifworld.ui.screens.full.FullDestination
import com.example.gifworld.ui.screens.home.HomeDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifWorldTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    topBarState: Boolean,
    currentRoute: String?
) {
    val titleRes = when (currentRoute) {
        HomeDestination.route -> HomeDestination.titleRes
        FullDestination.route -> FullDestination.titleRes
        else -> HomeDestination.titleRes
    }

    AnimatedVisibility(visible = topBarState) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(titleRes))
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier
        )
    }
}