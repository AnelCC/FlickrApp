package com.example.flickrapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun FlickrHomeScreen(navController: NavController, viewModel: FlickrViewModel) {
    Text(
        text = "Hello Flickr!",
        modifier = Modifier.fillMaxSize(),
        color = Color.Red
    )
}
