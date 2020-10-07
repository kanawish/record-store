package com.kanawish.common.model

fun interface Reducer<S> {
    /** Returns a new state, built on a currentState */
    fun reduce(currentState: S): S
}

/**
 * DSL function to help build intents from code blocks.
 *
 * NOTE: Magic of extension functions, (T)->T and T.()->T interchangeable.
 */
fun <S> action(block: S.() -> S) : Reducer<S> = object : Reducer<S> {
    override fun reduce(currentState: S): S = block(currentState)
}

/**
 * By delegating work to other models, repositories or services, we
 * can end up with situations where we don't need to update our ModelStore
 * state until the delegated work completes, but we need to base that work
 * on the current model state.
 *
 * Use the `sideEffect {}` DSL function for those situations.
 */
fun <S> sideEffect(block: S.() -> Unit) : Reducer<S> = object : Reducer<S> {
    override fun reduce(currentState: S): S = currentState.apply(block)
}
