package com.kanawish.recordstore.common

import kotlinx.coroutines.flow.Flow

/**
 * TODO: TBD
 * This allows us to group all the viewEvents from
 * one view in a single source.
 *
 * Consumers of this will be ViewModel under MVVM
 */
interface ViewEventFlow<E> {
    fun viewEvents(): Flow<E>
}