package com.example.flickrapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.ui.theme.ExtendedTheme
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesGrid(pictures: List<Picture>, onClick: (Picture) -> Unit) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(AppDimension.normalPadding),
        horizontalArrangement = Arrangement.spacedBy(AppDimension.normalPadding),
        verticalItemSpacing = AppDimension.normalPadding
    ) {
        items(pictures.size) {
            ItemBox(pictures[it]) {
                onClick.invoke(pictures[it])
            }
        }
    }
}

@Composable
fun ItemBox(item: Picture, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(Random.nextInt(100, 300).dp)
            .clip(RoundedCornerShape(AppDimension.normalPadding))
            .clickable { onClick?.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RoundedCornerShape(AppDimension.normalPadding))
                .background(ExtendedTheme.colors.primary)
                .size(80.dp),
            model = "${item.url}",
            contentDescription = "${item.title}",
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(AppDimension.normalPadding))
                .background(
                    Color(
                        Random.nextLong(0xFFFFFFFF)
                    ).copy(alpha = 1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${item.title}",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppDimension.xSmallPadding),
                textAlign = TextAlign.Center,
            )
        }
    }
}