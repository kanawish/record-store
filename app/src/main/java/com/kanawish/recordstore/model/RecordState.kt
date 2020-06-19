package com.kanawish.recordstore.model

/*
@startuml
hide methods
class RecordState << (D,orchid) >> {
    title: String
    description: String
    sku: String
    price: Int
    inventory: Int
}
@enduml
 */
data class RecordState(
    val title: String,
    val description: String,
    val sku: String,
    val price: Int,
    val inventory: Int
)

