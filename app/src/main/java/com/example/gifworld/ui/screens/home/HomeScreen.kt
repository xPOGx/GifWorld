package com.example.gifworld.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import com.example.gifworld.R
import com.example.gifworld.navigation.NavigationDestination
import com.example.gifworld.ui.screens.SharedViewModel
import com.example.gifworld.ui.tools.ErrorScreen
import com.example.gifworld.ui.tools.LoadingScreen
import com.example.gifworld.ui.tools.PagerStatusBox
import com.example.gifworld.utils.Status
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SharedViewModel,
    navigateToFullScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val state = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val shouldPaginate by remember {
        derivedStateOf {
            val one = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val two = state.layoutInfo.totalItemsCount - 1
            one == two
        }
    }

    LaunchedEffect(key1 = shouldPaginate) {
        with(uiState) {
            if (pagerStatus == Status.Done && shouldPaginate && canBePaginate)
                viewModel.loadMoreGifs()
        }
    }

    Column {
        SearchBox(
            input = uiState.searchQuery,
            onValueChange = { viewModel.updateUiState(uiState.copy(searchQuery = it)) },
            searchAction = {
                viewModel.initLoadGifs()
                if (uiState.status == Status.Loading) focusManager.clearFocus()
            },
            enabled = uiState.status != Status.Loading
        )

        when (uiState.status) {
            Status.Loading -> {
                LoadingScreen()
                LaunchedEffect(true) { coroutineScope.launch { state.scrollToItem(0) } }
            }

            Status.Done -> HomeBody(
                uiState = uiState,
                state = state,
                navigateToFullScreen = {
                    viewModel.updateUiState(
                        uiState.copy(
                            index = uiState.gifsList.indexOf(it)
                        )
                    )
                    navigateToFullScreen()
                },
                modifier = modifier
            )

            else -> ErrorScreen(tryAgainAction = viewModel::initLoadGifs)
        }
    }

    if (uiState.pagerStatus != Status.Done) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier.fillMaxSize()
        ) {
            PagerStatusBox(
                status = uiState.pagerStatus,
                tryAgain = { viewModel.loadMoreGifs() }
            )
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    input: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
    enabled: Boolean
) {
    OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { searchAction() }),
        trailingIcon = {
            Row(
                modifier = Modifier.animateContentSize()
            ) {
                IconButton(onClick = searchAction) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
                if (input.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        enabled = enabled,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.medium),
                vertical = dimensionResource(R.dimen.small)
            )
            .fillMaxWidth()
    )
}