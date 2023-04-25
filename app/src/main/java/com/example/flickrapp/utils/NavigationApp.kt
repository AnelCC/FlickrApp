package com.example.flickrapp.utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flickrapp.presentation.FlickrHomeScreen
import com.example.flickrapp.presentation.FlickrViewModel
import com.example.flickrapp.presentation.detail.DetailScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<FlickrViewModel>()

    NavHost(navController = navController, startDestination = DestinationScreen.HomeScreen.route) {
        composable(DestinationScreen.HomeScreen.route) {
            FlickrHomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.DetailScreen.route) {
            DetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object HomeScreen: DestinationScreen("home")
    object DetailScreen: DestinationScreen("detailScreen")
}


fun navigateTo(navController: NavController, destination: DestinationScreen) {
    navController.navigate(destination.route) {
        popUpTo(destination.route)
        launchSingleTop = true
    }
}
