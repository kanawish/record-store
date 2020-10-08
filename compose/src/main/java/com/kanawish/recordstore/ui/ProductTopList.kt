package com.kanawish.recordstore.ui

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.kanawish.recordstore.demo.recordProducts
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.ui.theming.MyComposedTheme

@Preview()
@Composable
fun ProductTopPreview() {
    MyComposedTheme {
        ProductTopList(products = recordProducts, top = 2)
    }
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