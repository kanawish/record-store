package com.kanawish.common.model

fun interface Reducer<S> {
    /** Returns a new state, built on a currentState */
    fun reduce(currentState: S): S
}
