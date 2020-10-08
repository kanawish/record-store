package com.kanawish.recordstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.demo.statelyProducts
import com.kanawish.recordstore.state.Product
import java.lang.IllegalStateException

// NOTE: E1
class SimpleProductViewModel : ViewModel() {
    private val _product = MutableLiveData(
        statelyProducts[0]
    )
    val product: LiveData<Product> = _product

    fun onNameChangeEvent(newName: String) {
        _product.value = _product.value?.copy(name = newName)
            ?: throw IllegalStateException("Product was null.")
    }
}
