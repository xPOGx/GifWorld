package com.example.gifworld.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import com.example.gifworld.R
import com.example.gifworld.network.GifData
import com.example.gifworld.ui.tools.GifComposable
import com.example.gifworld.ui.screens.SharedUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    uiState: SharedUiState,
    state: LazyStaggeredGridState,
    navigateToFullScreen: (GifData) -> Unit
) {
    LazyVerticalStaggeredGrid(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.between_grid_elements)),
        verticalItemSpacing = dimensionResource(R.dimen.between_grid_elements),
        columns = StaggeredGridCells.Fixed(LocalConfiguration.current.screenWidthDp / 100),
        state = state,
        modifier = modifier
    ) {
        items(uiState.gifsList) { gif ->
            GifComposable(
                gifUrl = gif.images.preview.url,
                contentScale = ContentScale.Crop,
                onClick = { navigateToFullScreen(gif) }
            )
        }
    }
}