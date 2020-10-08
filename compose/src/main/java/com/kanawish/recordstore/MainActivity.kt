package com.kanawish.recordstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductEditorObservableStore
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.ui.ProductCard
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.viewmodel.SimpleProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import toothpick.ktp.delegate.inject

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    val productEditorObservableStore: ProductEditorObservableStore by inject()
    val productEditorFlowStore: ProductEditorFlowStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposedTheme {
                // NOTE: E1
                LiveDataExample()
//                ProductTopList(top = 2, products = recordProducts)
            }
        }
    }
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

