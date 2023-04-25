package com.example.flickrapp.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flickrapp.R
import com.example.flickrapp.presentation.home.ImagesGrid
import com.example.flickrapp.presentation.utils.Loading
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.utils.TopAppBarFlickr

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlickrHomeScreen(navController: NavController, viewModel: FlickrViewModel) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val pictures by viewModel.pictures.collectAsStateWithLifecycle()


    Column() {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBarFlickr(title = stringResource(R.string.app_name)) {
                navController.backQueue
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
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchText,
                    onValueChange = { viewModel.onSearchTextChange(it) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    },
                    placeholder = { Text(text = stringResource(R.string.search)) }
                )
                Spacer(modifier = Modifier.height(AppDimension.normalPadding))
                viewModel.onLoadInitData()
                if (isSearching) {
                    Loading()
                } else {
                    ImagesGrid(pictures)
                }
            }
        }
    }
}
