package com.kanawish.recordstore.ui.foo

import androidx.lifecycle.ViewModel
import timber.log.Timber

class BarViewModel : ViewModel() {
    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCleared() {
        Timber.d("${javaClass.simpleName} onCleared() [${this}]")
    }
}
