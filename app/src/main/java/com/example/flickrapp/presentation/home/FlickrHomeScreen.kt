package com.example.flickrapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flickrapp.R
import com.example.flickrapp.presentation.home.ImagesGrid
import com.example.flickrapp.presentation.utils.Loading
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.utils.AlertDialog
import com.example.flickrapp.utils.DestinationScreen
import com.example.flickrapp.utils.Resource
import com.example.flickrapp.utils.TopAppBarFlickr
import com.example.flickrapp.utils.navigateTo

@Composable
fun FlickrHomeScreen(navController: NavController, viewModel: FlickrViewModel) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val pictures by viewModel.pictures.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showErrorDialog by remember { mutableStateOf(false) }

    when (uiState) {
        is Resource.Success -> {
            viewModel.isSearching(false)
        }
        is Resource.Error -> {
            viewModel.isSearching(false)
            showErrorDialog = true
        }
        is Resource.Loading -> {
            viewModel.isSearching(true)
            showErrorDialog = false
        }
    }

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBarFlickr(stringResource(R.string.app_name))
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

                if (showErrorDialog) {
                    AnimatedVisibility(visible = uiState is Resource.Error) {
                        AlertDialog(
                            title = stringResource((uiState as Resource.Error).message.asString().toInt()),
                            actionText = stringResource(R.string.close),
                            shouldDismiss = {
                                viewModel.noInfo()
                                showErrorDialog = it
                            }
                        )
                    }
                }

                AnimatedVisibility(visible = uiState is Resource.Loading) {
                    Loading()
                }

                AnimatedVisibility(visible = uiState is Resource.Success) {
                    ImagesGrid(pictures)  {
                        viewModel.onSelectItem(it)
                        navigateTo(navController, DestinationScreen.DetailScreen)
                    }
                }

            }
        }
    }
}
