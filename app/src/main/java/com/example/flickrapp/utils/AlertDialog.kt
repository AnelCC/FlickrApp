package com.example.flickrapp.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.example.flickrapp.ui.theme.AppDimension
import com.example.flickrapp.ui.theme.ExtendedTheme

@Composable
fun AlertDialog(title: String, actionText: String, shouldDismiss: (Boolean) -> Unit) {

    Dialog(onDismissRequest = { shouldDismiss(false) }) {
        Surface(
            shape = RoundedCornerShape(AppDimension.normalPadding),
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(AppDimension.largePadding)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = title)
                    }
                    Spacer(modifier = Modifier.height(AppDimension.largePadding))
                    Box(
                        modifier = Modifier
                            .padding(AppDimension.normalSizeElement, AppDimension.defaultPadding, AppDimension.normalSizeElement, AppDimension.defaultPadding)) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = AppDimension.normalPadding,
                                    bottom = AppDimension.normalPadding
                                )
                                .height(AppDimension.normalSizeElement),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor =  ExtendedTheme.colors.white,
                                contentColor =  ExtendedTheme.colors.primary,
                            ),
                            shape = RoundedCornerShape(percent = 50),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = AppDimension.xSmallPadding,
                                pressedElevation = AppDimension.smallPadding
                            ),
                            onClick = { shouldDismiss(false) }) {
                            Text(text = actionText)
                        }
                    }
                }
            }
        }

    }
}