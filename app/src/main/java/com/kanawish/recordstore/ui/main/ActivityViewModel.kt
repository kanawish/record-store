package com.kanawish.recordstore.ui.main

import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.model.EditorModel
import timber.log.Timber
import toothpick.ktp.delegate.inject

class ActivityViewModel : ViewModel() {

    val editorModel: EditorModel by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCleared() {
        Timber.d("${javaClass.simpleName} onCleared() [${this}]")
    }
}
