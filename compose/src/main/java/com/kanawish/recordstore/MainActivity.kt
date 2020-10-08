package com.kanawish.recordstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.kanawish.common.model.Reducer
import com.kanawish.recordstore.model.ProductObservableStore
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.ui.LiveDataExample
import com.kanawish.recordstore.ui.ProductCard
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import toothpick.ktp.delegate.inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val productObservableStore: ProductObservableStore by inject() // E2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposedTheme {
                RxJavaExample(
                    subscribeAsState = productObservableStore.modelState().subscribeAsState(Product()),
                    processor = productObservableStore::process)
            }
        }
    }
}

@Composable
private fun NameEditField(
    product: Product,
    onValueChange: (String)->Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = product.name,
        onValueChange = onValueChange,
        label = { Text("Edit here") },
        backgroundColor = Color(0xffb9b9b9)
    )
}

@Composable
fun EditorButton(label:String, onClick:()->Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) { Text(label) }
}

fun nameChange(newName: String): Reducer<Product> = Reducer { it.copy(name = newName) }
fun priceChange(change: Long): Reducer<Product> = Reducer { it.copy(price = it.price + change) }

/**
 * NOTE: E2
 */
@Composable
fun RxJavaExample(subscribeAsState: State<Product>, processor: (Reducer<Product>) -> Unit) {
    val product by subscribeAsState
    Column {
        ProductCard(product = product)
        NameEditField(product) { processor(nameChange(it)) }
        Row {
            EditorButton("-1") { processor(priceChange(-100)) }
            EditorButton("+1") { processor(priceChange(100)) }
        }
    }
}
