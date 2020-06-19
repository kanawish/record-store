package com.kanawish.recordstore.model.backend

import com.kanawish.recordstore.model.RecordEditorState
import com.kanawish.recordstore.model.RecordState
import toothpick.config.Module

/**
 * Binding Module for EditorModelModule
 */
object EditorModelModule : Module() {
    init {
        bind(RecordEditorState::class.java).toInstance(
                RecordEditorState.Editing(
                        RecordState(
                                title = "",
                                description = "",
                                sku = "",
                                price = 0,
                                inventory = 0
                        )
                )
        )
        bind(BaseUrl::class.java).toInstance("https://kanawishcurriculumendpoint.firebaseio.com/")
        bind(RecordStoreRestApi::class.java).toProvider(RecordStoreRestApiProvider::class.java)
    }
}