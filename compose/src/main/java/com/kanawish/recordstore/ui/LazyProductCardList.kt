package com.kanawish.recordstore.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.kanawish.recordstore.demo.recordProducts
import com.kanawish.recordstore.state.Product

@Preview
@Composable
fun LazyListPreview() {
    LazyProductCardList(products = recordProducts)
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
