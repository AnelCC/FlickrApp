package com.example.flickrapp.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.flickrapp.R
import com.example.flickrapp.ui.theme.AppDimension.Companion.xSmallPadding
import com.example.flickrapp.ui.theme.ExtendedTheme

@Composable
fun TopAppBarFlickr(title: String, imageVectorType: ImageVector = ImageVector.NONE, onClickBack: (() -> Unit)? = null) {
    val imageVector = when (imageVectorType) {
        ImageVector.NONE -> null
        ImageVector.ARROW -> Icons.Filled.ArrowBack
        ImageVector.CLOSE -> Icons.Filled.Close
    }

    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onClickBack?.invoke()
            }) {
                imageVector?.let {
                    Icon(
                        imageVector,
                        stringResource(id = R.string.go_back)
                    )
                }
            }
        },
        backgroundColor = ExtendedTheme.colors.primary,
        contentColor = ExtendedTheme.colors.white,
        elevation = xSmallPadding,
    )
}

enum class ImageVector(val imageVectorId: Int){
    NONE(0),
    ARROW(1),
    CLOSE(2)
}
