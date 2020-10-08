package com.kanawish.recordstore.model

import com.kanawish.common.model.FlowModelStore
import com.kanawish.common.model.Reducer
import com.kanawish.recordstore.state.ProductEditorState
import com.kanawish.recordstore.state.ProductEditorState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProductEditorFlowStore @Inject constructor() :
    FlowModelStore<ProductEditorState>(Closed)

/**
 * Reducers created here will check the subtype of the current state
 * and make sure the current state is of the right type.
 */
inline fun <reified S : ProductEditorState> editorReducer (
    crossinline block: S.() -> ProductEditorState
) : Reducer<ProductEditorState> {
    return Reducer {
        (it as? S)?.block()
            ?: throw IllegalStateException("editorReducer encountered an inconsistent State. [Looking for ${S::class.java} but was ${it.javaClass}]")
    }
}
