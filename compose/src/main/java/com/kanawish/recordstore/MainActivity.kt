package com.kanawish.recordstore

import android.os.Bundle
import android.widget.Space
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kanawish.common.composable.NetworkImageComponentPicasso
import com.kanawish.recordstore.demo.recordProducts
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductEditorObservableStore
import com.kanawish.recordstore.state.ProductEditorState.*
import com.kanawish.recordstore.state.Product
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

        val stateName: MutableState<String> = mutableStateOf("Android")

        setContent {
            MyComposedTheme {
                LazyProductCardList(products = recordProducts)
            }
        }
        stateName.value = "Bob"
    }

    fun firebase() {
        val database = Firebase.database
        val ref = database.getReference("bar")
        ref.child(Closed::class.simpleName!!).setValue(Closed::class.simpleName!!)
        ref.child(Editing::class.simpleName!!).setValue(Editing(Product("Hello Product")))
        ref.child(Saving::class.simpleName!!).setValue(Saving(Product("Saving Product")))
        ref.child(Deleting::class.simpleName!!).setValue(Deleting(Product("Deleting Product")))
        ref.child(Error::class.simpleName!!).setValue(Error(Product("Error Product"), "Error Message"))
    }
}

@Composable
fun ProductDemo(product: Product, onDo: () -> Unit, onUndo: () -> Unit) {

}

@Composable
fun Greeting(nameState: State<String>) {
    Timber.i("Greeting with ${nameState.value}")

    // Avoid re-generating a different state for every recomposition.
    val name by remember { nameState }

    Column {
        Timber.i("Column with $name")
        Text(text = "Hello $name!")
    }
}


class PriceFormatter() {
    val defaultLocale = Locale.getDefault()
    val currency = Currency.getInstance(defaultLocale)
    val currencyFormat = NumberFormat.getCurrencyInstance(defaultLocale)

    fun format(price:Long):String {
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
fun ProductTop(top: Int, products: List<Product>) {
    ScrollableColumn {
        products
            .sortedBy(Product::price)
            .take(top)
            .forEach { product -> 
                ProductCard(product) {}
                Spacer(Modifier.preferredHeight(4.dp))
            }
    }
}

@Composable
fun ProductCard(product: Product, onPriceClick: () -> Unit) {
    val formattedPrice = remember {
        product.price.currencyFormat()
    }

    val imageModifier = Modifier
        .fillMaxWidth()
        .preferredHeight(140.dp)
        .clip(RoundedCornerShape(18.dp))

    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            CoilImage(
                data = product.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )

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
fun LazyProductCardList(products:List<Product>) {
    LazyColumnForIndexed(items = products) { index, product ->
        ProductCard(product = product) {}
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposedTheme {
        Greeting(mutableStateOf("Android"))
    }
}

@Preview
@Composable
fun ProductPreview() {
    MyComposedTheme {
        ProductCard(
            Product(
                name = "MadLib Beats | Beat Konducta",
                description = "Otis Jackson Jr. (born October 24, 1973), known professionally as Madlib, is an American DJ, music producer, multi-instrumentalist, and rapper. He is one of the most prolific and critically acclaimed hip hop producers of the 2000s and has collaborated with different hip hop artists, under a variety of pseudonyms, including with MF DOOM (as Madvillain), as well as J Dilla (as Jaylib). Madlib has described himself as a \"DJ first, producer second, and MC last,\"[1][2] and he has done several projects as a DJ, mixer, or remixer.",
                imageUrl = "file:///android_asset/records/madlib_movie_scenes_front.jpg",
                price = 1999
            ), {}

        )
    }
}

@Preview()
@Composable
fun ProductTopPreview() {
    MyComposedTheme {
        ProductTop(products = recordProducts, top = 2)
    }
}