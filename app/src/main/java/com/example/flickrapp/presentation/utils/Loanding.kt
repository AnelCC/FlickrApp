package com.example.flickrapp.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.ui.theme.ExtendedTheme

@Composable
fun Loading(){
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(AppDimension.xlargeSizeElement),
            color = ExtendedTheme.colors.primary
        )
    }
}