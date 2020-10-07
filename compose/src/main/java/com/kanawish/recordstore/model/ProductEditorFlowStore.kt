package com.kanawish.recordstore.model

import com.kanawish.common.model.FlowModelStore
import com.kanawish.recordstore.state.ProductEditorState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProductEditorFlowStore
@Inject constructor(startingState: ProductEditorState) :
    FlowModelStore<ProductEditorState>(startingState)