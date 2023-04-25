package com.example.flickrapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flickrapp.R
import com.example.flickrapp.presentation.Picture
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.ui.theme.ExtendedTheme
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesGrid(pictures: List<Picture>) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pictures.size) {
            ItemBox(pictures[it])
        }
    }
}

@Composable
fun ItemBox(item: Picture) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(Random.nextInt(100, 300).dp)
            .clip(RoundedCornerShape(AppDimension.smallPadding))
            .background(
                Color(
                    Random.nextLong(0xFFFFFFFF)
                ).copy(alpha = 1f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_launcher_foreground,
            ),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(AppDimension.normalPadding))
                .background(ExtendedTheme.colors.primary)
                .size(80.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = "${item.name}",
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimension.normalPadding),
            textAlign = TextAlign.Center
        )
    }
}