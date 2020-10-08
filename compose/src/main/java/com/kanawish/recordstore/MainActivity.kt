package com.kanawish.recordstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.kanawish.common.model.Reducer
import com.kanawish.recordstore.demo.statelyProducts
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductObservableStore
import com.kanawish.recordstore.model.editorReducer
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.state.ProductEditorState
import com.kanawish.recordstore.state.ProductEditorState.*
import com.kanawish.recordstore.ui.ProductCard
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.viewmodel.SimpleProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import toothpick.ktp.delegate.inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val productObservableStore: ProductObservableStore by inject() // E2
    private val productEditorFlowStore: ProductEditorFlowStore by inject() // E3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposedTheme {
                // NOTE: E3
                FlowExample(
                    productEditorFlowStore.modelState().collectAsState(initial = Closed),
                    productEditorFlowStore::process
                )
/*
                // NOTE: E2
                // We're taking a different approach here.
                RxJavaExample(
                    productObservableStore.modelState().subscribeAsState(initial = Product()),
                    productObservableStore::process
                )
*/

                // NOTE: E1
//                LiveDataExample()

                // Screenshot material
//                ProductTopList(top = 2, products = recordProducts)
            }
        }
    }
}

/**
 * Bunch of example reducers we can trigger for processing. Notice the equivalence with
 * our UML diagram.
 * NOTE: E3
 */
fun openNew() = editorReducer<Closed> { edit(statelyProducts[2]) } // Hardcoded for the demo.

fun edit(productReducer:Reducer<Product>) = editorReducer<Editing> { edit(productReducer) }
fun cancel() = editorReducer<Editing> { cancel() }
fun save() = editorReducer<Editing> { save().saved() } // Skips the async step for demo.
fun delete() = editorReducer<Editing> { delete().deleted() } // Skips the async step for demo.

// E2 & E3
@Composable
fun EditorButton(label:String, onClick:()->Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) { Text(label) }
}

// E3
@Composable
fun ProductEditor(state:ProductEditorState, processor: (Reducer<ProductEditorState>) -> Unit) {
    Timber.i("ProductEditor called.")
    when(state) {
        is Closed -> {
            Timber.i("Closed")
            EditorButton("Create New Product") { processor(openNew()) }
        }
        is Editing -> {
            Timber.i("Editing")
            Column {
                ProductCard(state.product)
                NameEditField(state.product) { processor(edit(nameChange(it))) }
                Row {
                    EditorButton("-1") { processor(edit(priceChange(-100))) }
                    EditorButton("+1") { processor(edit(priceChange(100))) }
                }
                Spacer(Modifier.preferredHeight(12.dp))
                Row {
                    EditorButton("Delete") { processor(delete()) }
                    EditorButton("Cancel") { processor(cancel()) }
                    EditorButton("Save") { processor(save()) }
                }
            }
        }
        is Saving -> {
            Timber.i("Saving")
            ProductCard(product = state.product)
        }
        is Deleting -> {
            Timber.i("Deleting")
            ProductCard(product = state.product)
        }
    }
}

/**
 * NOTE: E3
 */
@Composable
fun FlowExample(collectAsState: State<ProductEditorState>, processor: (Reducer<ProductEditorState>) -> Unit) {
    val editorState by collectAsState

    ProductEditor(editorState, processor)
}

// NOTE: E2 We map Compose events to reducers.
fun nameChange(newName: String): Reducer<Product> = Reducer { it.copy(name = newName) }
fun priceChange(change: Long): Reducer<Product> = Reducer { it.copy(price = it.price + change) }

/**
 * NOTE: E2
 */
@Composable fun RxJavaExample(subscribeAsState: State<Product>, processor: (Reducer<Product>) -> Unit) {
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

// NOTE: E1
@Composable
fun LiveDataExample() {
    val viewModel:SimpleProductViewModel = viewModel()

    // NOTE: LD fun, dealing with potential nulls.
    val product by viewModel.product.observeAsState(Product())
    Column {
        ProductCard(product = product)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = product.name,
            onValueChange = { viewModel.onNameChangeEvent(it) },
            label = { Text("Edit here") },
            backgroundColor = Color(0xffb9b9b9)
        )
    }
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

