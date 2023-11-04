package com.example.gifworld.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gifworld.navigation.GifWorldNavHost
import com.example.gifworld.navigation.GifWorldTopAppBar
import com.example.gifworld.ui.screens.home.HomeDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifWorldApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var topBarState by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    topBarState = currentRoute == HomeDestination.route

    Scaffold(
        topBar = {
            GifWorldTopAppBar(
                topBarState = topBarState,
                currentRoute = currentRoute,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        GifWorldNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
