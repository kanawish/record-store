package com.kanawish.recordstore.state

sealed class ProductEditorState {
    object Closed : ProductEditorState() {
        fun editProduct(product: Product) = Editing(product)
    }

    data class Editing(val product: Product) : ProductEditorState() {
        fun edit(block: Product.() -> Product) = copy(product = product.block())
        fun save() = Saving(product)
        fun delete() = Deleting(product)
        fun cancel() = Closed
    }

    data class Saving(val product: Product) : ProductEditorState() {
        fun saved() = Closed
        fun error(msg: String) = Error(product, msg)
    }

    data class Deleting(val product: Product) : ProductEditorState() {
        fun deleted() = Closed
        fun error(msg: String) = Error(product, msg)
    }

    data class Error(val product: Product, val msg: String) {
        fun edit(product: Product) = Editing(product)
        fun cancel() = Closed
    }
}
