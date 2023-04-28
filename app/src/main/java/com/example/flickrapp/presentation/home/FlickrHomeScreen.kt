package com.example.flickrapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
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
    val searchList by viewModel.searchList.collectAsStateWithLifecycle()

    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var isExpanded by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    when (uiState) {
        is Resource.Error -> {
            showErrorDialog = true
        }

        is Resource.Loading -> {
            showErrorDialog = false
        }

        else -> {}
    }

    Column {
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
                Box() {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusable(true)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                            .clickable { isExpanded = !isExpanded },
                        singleLine = true,
                        value = searchText,
                        onValueChange = {
                            isExpanded = true
                            viewModel.getSearchHistory()
                            viewModel.onSearchTextChange(it)
                        },
                        trailingIcon = {
                            if (searchText.isNullOrBlank()) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search),
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.close),
                                    modifier = Modifier.clickable {
                                        viewModel.onSaveSearchText(searchText)
                                        viewModel.resetTextChanged()
                                    }
                                )
                            }
                        },
                        placeholder = { Text(text = stringResource(R.string.search)) }
                    )
                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                        modifier = Modifier
                            .imePadding()
                            .width(with(LocalDensity.current){textFieldSize.width.toDp()}),
                        properties = PopupProperties(focusable = false)
                    ) {
                        searchList.forEach { s ->
                            DropdownMenuItem(onClick = {
                                viewModel.onSearchTextChange(s.searchText)
                                isExpanded = false
                            }) {
                                Text(text = s.searchText)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimension.normalPadding))

                if (showErrorDialog) {
                    AnimatedVisibility(visible = uiState is Resource.Error) {
                        AlertDialog(
                            title = stringResource(
                                (uiState as Resource.Error).message.asString().toInt()
                            ),
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
                    ImagesGrid(pictures) {
                        viewModel.onSelectItem(it)
                        navigateTo(navController, DestinationScreen.DetailScreen)
                    }
                }

            }
        }
    }
}
