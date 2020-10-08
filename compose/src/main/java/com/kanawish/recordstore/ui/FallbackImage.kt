package com.kanawish.recordstore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.kanawish.recordstore.R

@Composable
private fun FallbackImage() {
    val fallBackImageAsset: ImageAsset = imageResource(id = R.drawable.joy_division_front)
    Image(
        asset = fallBackImageAsset,
        modifier = Modifier.fillMaxWidth()
            .preferredHeight(140.dp)
            .clip(RoundedCornerShape(18.dp)),
        contentScale = ContentScale.Crop
    )
}