package com.kanawish.recordstore.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.viewModel
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.viewmodel.SimpleProductViewModel

@Composable
fun LiveDataExample() {
    val viewModel: SimpleProductViewModel = viewModel()

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