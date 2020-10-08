package com.kanawish.recordstore.state

import com.kanawish.common.model.Reducer


/**
 * Example 3 - Product Editor
 */
sealed class ProductEditorState {
    object Closed : ProductEditorState() {
        fun open(product: Product) = Editing(product)
    }

    data class Editing(val product: Product) : ProductEditorState() {
        fun edit(reducer: Reducer<Product>) = copy(product = reducer.reduce(product))
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

/*
    UML Diagram we're aiming for:

    @startuml
    [*] --> CLOSED
    CLOSED --> EDITING : edit()

    EDITING : product
    EDITING --> EDITING : edit()

    EDITING -down-> SAVING : save()
    EDITING -down-> DELETING : delete()
    EDITING -left-> CLOSED : cancel()

    DELETING -up-> CLOSED : deleted()
    DELETING --> ERROR : error(msg)
    DELETING : product

    SAVING -up-> CLOSED : saved()
    SAVING --> ERROR : error(msg)
    SAVING : product

    ERROR--> EDITING: edit()
    ERROR : product
    @enduml
 */
