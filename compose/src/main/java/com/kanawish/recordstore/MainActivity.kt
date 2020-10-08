package com.kanawish.recordstore

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.kanawish.recordstore.demo.recordProducts
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductEditorObservableStore
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.ui.theming.typography
import com.kanawish.recordstore.viewmodel.SimpleProductViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import toothpick.ktp.delegate.inject
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    // Which should you use? [Snark: cue galaxy brain gifs]
    val simpleProductViewModel by viewModels<SimpleProductViewModel>()
    val productEditorObservableStore: ProductEditorObservableStore by inject()
    val productEditorFlowStore: ProductEditorFlowStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposedTheme {
                ProductTopList(top = 2, products = recordProducts)
            }
        }
    }
}

@Composable
fun ProductDemo(product: Product, onDo: () -> Unit, onUndo: () -> Unit) {
}

class PriceFormatter() {
    private val defaultLocale: Locale = Locale.getDefault()
    private val currency: Currency = Currency.getInstance(defaultLocale)
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(defaultLocale)

    fun format(price: Long): String {
        return currencyFormat.format(BigDecimal(price).movePointLeft(currency.defaultFractionDigits))
    }
}

fun Long.currencyFormat(): String {
    val defaultLocale = Locale.getDefault()
    val currency = Currency.getInstance(defaultLocale)
    val currencyFormat = NumberFormat.getCurrencyInstance(defaultLocale)

    return currencyFormat.format(BigDecimal(this).movePointLeft(currency.defaultFractionDigits))
}

@Composable
fun ProductTopList(top: Int, products: List<Product>) {
    ScrollableColumn(
        Modifier.padding(4.dp)
    ) {
        products
            .sortedBy(Product::price)
            .take(top)
            .forEach { product ->
                ProductCard(product)
                Spacer(Modifier.preferredHeight(4.dp))
            }
    }
}

@Composable
fun ProductCard(product: Product, onPriceClick: () -> Unit = {}) {
    val formattedPrice = remember {
        product.price.currencyFormat()
    }

    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            FallbackImage()
//            CoilProductImage(product.imageUrl)
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
                Text("Buy for $formattedPrice")
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

@Composable
fun LazyProductCardList(products: List<Product>) {
    LazyColumnForIndexed(
        items = products,
        modifier = Modifier.padding(4.dp)
    ) { index, product ->
        ProductCard(product = product)
        if (index != products.lastIndex) {
            Spacer(Modifier.preferredHeight(4.dp))
        }
    }
}

@Preview
@Composable
fun LazyListPreview() {
    LazyProductCardList(products = recordProducts)
}

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

@Preview()
@Composable
fun ProductTopPreview() {
    MyComposedTheme {
        ProductTopList(products = recordProducts, top = 2)
    }
}