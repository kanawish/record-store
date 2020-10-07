package com.kanawish.recordstore.state

data class Product(
    val uuid:String? = null,
    val name:String = "",
    val description:String = "",
    val imageUrl:String = "",
    val price:Long = 0
)
