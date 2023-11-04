package com.example.gifworld.ui.screens.full

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.gifworld.R
import com.example.gifworld.network.GifData
import com.example.gifworld.ui.tools.FloatingButton
import com.example.gifworld.ui.tools.FloatingButtonsRow
import com.example.gifworld.ui.tools.GifComposable
import com.example.gifworld.ui.tools.PagerStatusBox
import com.example.gifworld.utils.ScreenManager
import com.example.gifworld.utils.Status
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullBody(
    modifier: Modifier = Modifier,
    gifsList: List<GifData>,
    pagerState: PagerState,
    pagerStatus: Status,
    loadMoreGifs: () -> Unit,
    navigateUp: () -> Unit,
    banGif: (GifData) -> Unit
) {
    val size = gifsList.size
    if (pagerState.currentPage == size - 1 && pagerStatus != Status.Loading) loadMoreGifs()

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = size,
            state = pagerState,
            modifier = Modifier.align(Alignment.Center)
        ) {
            val gifData = gifsList[it]
            GifComposable(
                gifUrl = gifData.images.original.webp,
                modifier = Modifier.fillMaxSize()
            )
            FloatingButtonsRow(
                navigateUp = navigateUp,
                banGif = {
                    banGif(gifData)
                    navigateUp()
                },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        ControlPanel(
            pagerState = pagerState,
            listSize = size,
            modifier = if (ScreenManager.isLandscape()) Modifier.align(Alignment.Center)
            else Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(R.dimen.medium))
        )

        if (pagerStatus != Status.Done) {
            PagerStatusBox(
                status = pagerStatus,
                tryAgain = loadMoreGifs,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    listSize: Int
) {
    val currentPage = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        FloatingButton(
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(currentPage.dec()) }
            },
            imageVector = Icons.Default.KeyboardArrowLeft,
            enabled = currentPage != 0
        )
        FloatingButton(
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(currentPage.inc()) }
            },
            imageVector = Icons.Default.KeyboardArrowRight,
            enabled = currentPage != listSize - 1
        )
    }
}
