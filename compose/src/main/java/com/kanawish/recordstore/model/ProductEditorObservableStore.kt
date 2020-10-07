package com.kanawish.recordstore.model

import com.kanawish.common.model.ObservableModelStore
import com.kanawish.recordstore.state.ProductEditorState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductEditorObservableStore
@Inject constructor(startingState: ProductEditorState) :
    ObservableModelStore<ProductEditorState>(startingState)
