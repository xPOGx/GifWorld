package com.example.gifworld.ui.screens.full

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gifworld.R
import com.example.gifworld.navigation.NavigationDestination
import com.example.gifworld.ui.screens.SharedViewModel

object FullDestination : NavigationDestination {
    override val route: String = "full"
    override val titleRes: Int = R.string.gif
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullScreen(
    modifier: Modifier = Modifier,
    viewModel: SharedViewModel,
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(uiState.index)
    val context = LocalContext.current

    FullBody(
        gifsList = uiState.gifsList,
        pagerState = pagerState,
        pagerStatus = uiState.pagerStatus,
        loadMoreGifs = { viewModel.loadMoreGifs() },
        navigateUp = navigateUp,
        banGif = { viewModel.updateBan(context, it) },
        modifier = modifier
    )
}