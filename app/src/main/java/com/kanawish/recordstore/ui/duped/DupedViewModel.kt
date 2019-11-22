package com.kanawish.recordstore.ui.duped

import androidx.lifecycle.ViewModel
import timber.log.Timber

class DupedViewModel : ViewModel() {
    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCleared() {
        Timber.d("${javaClass.simpleName} onCleared() [${this}]")
    }
}
