package com.kanawish.recordstore.state

import androidx.compose.runtime.Immutable

/**
 * Our simple Product class.
 * NOTE: https://developer.android.com/reference/kotlin/androidx/compose/runtime/Immutable
 *   Basically an indicator Compose can use to optimize.
 */
@Immutable
data class Product(
    val uuid:String? = null,
    val name:String = "",
    val description:String = "",
    val imageUrl:String = "",
    val price:Long = 0
)
