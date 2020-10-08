package com.kanawish.recordstore.model

import com.kanawish.common.model.ObservableModelStore
import com.kanawish.recordstore.demo.statelyProducts
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.state.ProductEditorState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductObservableStore @Inject constructor() :
    ObservableModelStore<Product>(statelyProducts[1])
