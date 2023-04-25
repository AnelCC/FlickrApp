package com.example.flickrapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flickrapp.R
import com.example.flickrapp.presentation.FlickrViewModel
import com.example.flickrapp.presentation.home.ItemBox
import com.example.flickrapp.presentation.utils.Loading
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.utils.ImageVector
import com.example.flickrapp.utils.TopAppBarFlickr


@Composable
fun DetailScreen(navController: NavController, viewModel: FlickrViewModel) {
    val selectedPicture by viewModel.selectedPicture.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val pictures by viewModel.pictures.collectAsStateWithLifecycle()


    Column() {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val title = selectedPicture?.title ?: stringResource(R.string.app_name)
            TopAppBarFlickr(title, ImageVector.ARROW) {
                navController.popBackStack()
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppDimension.smallPadding)
            ) {
                Spacer(modifier = Modifier.height(AppDimension.normalPadding))
                if (isSearching) {
                    Loading()
                } else {
                    selectedPicture?.let {
                        ItemBox(it)
                    }
                }
            }
        }
    }
}
