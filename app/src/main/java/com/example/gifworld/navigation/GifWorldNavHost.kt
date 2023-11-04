package com.example.gifworld.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gifworld.AppViewModelProvider
import com.example.gifworld.activity.MainActivity
import com.example.gifworld.ui.screens.full.FullDestination
import com.example.gifworld.ui.screens.full.FullScreen
import com.example.gifworld.ui.screens.home.HomeDestination
import com.example.gifworld.ui.screens.home.HomeScreen
import com.example.gifworld.ui.screens.SharedViewModel

@Composable
fun GifWorldNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val sharedViewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val activity = LocalContext.current as MainActivity

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            activity.showSystemUi()
            HomeScreen(
                viewModel = sharedViewModel,
                navigateToFullScreen = { navController.navigate(FullDestination.route) }
            )
        }
        composable(route = FullDestination.route) {
            activity.hideSystemUI()
            FullScreen(
                viewModel = sharedViewModel,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}