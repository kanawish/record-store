package com.kanawish.recordstore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.kanawish.recordstore.tools.currencyFormat
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.tools.PriceFormatter
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.ui.theming.typography
import dev.chrisbanes.accompanist.coil.CoilImage

@Preview
@Composable
fun ProductPreview() {
    MyComposedTheme {
        ProductCard(
            Product(
                name="Joy Division, 'Unknown Pleasures'",
                description = "Designer Peter Saville's decision to go with pulsar radio waves is right up there with Martin Hannett’s spellbinding production in making this album a goth classic.\n\nDisney's Mickey Mouse shirt parody four decades later only reaffirmed its legend.",
                imageUrl = "file:///android_asset/records/joy_division_front.jpg",
                price = 999
            )
        )
    }
}

@Composable
fun ProductCard(product: Product, onPriceClick: () -> Unit = {}) {
    val formatter = remember { PriceFormatter() }

    Card {
        Column(modifier = Modifier.padding(16.dp)) {
//            FallbackImage() // Using this for screenshots
            CoilProductImage(product.imageUrl)
            Spacer(modifier = Modifier.preferredHeight(12.dp))
            Text(text = product.name, style = typography.h6)
            Text(
                text = product.description,
                style = typography.body2,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 8.dp).align(Alignment.End),
                onClick = onPriceClick
            ) {
                Text("Buy for ${formatter.format(product.price)}")
            }
        }
    }
}

@Composable
private fun CoilProductImage(imageUrl: String) {
    CoilImage(
        data = imageUrl,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
            .preferredHeight(140.dp)
            .clip(RoundedCornerShape(18.dp))
    )
}

