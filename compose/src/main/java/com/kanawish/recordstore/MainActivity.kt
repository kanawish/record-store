package com.kanawish.recordstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.kanawish.common.model.Reducer
import com.kanawish.recordstore.demo.statelyProducts
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductObservableStore
import com.kanawish.recordstore.model.editorReducer
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.state.ProductEditorState
import com.kanawish.recordstore.ui.ProductCard
import com.kanawish.recordstore.ui.theming.MyComposedTheme
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
                FlowExample(
                    productEditorFlowStore.modelState().collectAsState(initial = ProductEditorState.Closed),
                    productEditorFlowStore::process
                )
            }
        }
    }
}

fun openNew() = editorReducer<ProductEditorState.Closed> { open(statelyProducts[2]) } // Hardcoded for the demo.

fun edit(productReducer:Reducer<Product>) = editorReducer<ProductEditorState.Editing> { edit(productReducer) }
fun cancel() = editorReducer<ProductEditorState.Editing> { cancel() }
fun save() = editorReducer<ProductEditorState.Editing> { save().saved() } // Skips the async step for demo.
fun delete() = editorReducer<ProductEditorState.Editing> { delete().deleted() } // Skips the async step for demo.

@Composable
fun FlowExample(collectAsState: State<ProductEditorState>, processor: (Reducer<ProductEditorState>) -> Unit) {
    val editorState by collectAsState

    ProductEditor(editorState, processor)
}

@Composable
fun ProductEditor(state:ProductEditorState, processor: (Reducer<ProductEditorState>) -> Unit) {
    Timber.i("ProductEditor called.")
    when(state) {
        is ProductEditorState.Closed -> {
            Timber.i("Closed")
            EditorButton("Create New Product") { processor(openNew()) }
        }
        is ProductEditorState.Editing -> {
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
        is ProductEditorState.Saving -> {
            Timber.i("Saving")
            ProductCard(product = state.product)
        }
        is ProductEditorState.Deleting -> {
            Timber.i("Deleting")
            ProductCard(product = state.product)
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
