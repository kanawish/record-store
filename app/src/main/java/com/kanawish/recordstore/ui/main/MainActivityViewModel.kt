package com.kanawish.recordstore.ui.main

import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainActivityViewModel : ViewModel() {
    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCleared() {
        Timber.d("${javaClass.simpleName} onCleared() [${this}]")
    }
}
