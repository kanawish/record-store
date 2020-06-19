package com.kanawish.recordstore.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Call cancel() on this to kill off processing, etc.
 */
@FlowPreview
@ExperimentalCoroutinesApi
open class FlowModelRepository<S>(startingState: S) {
    /**
     * NOTE: Intent are supposed to be quick and non blocking.
     *   It's fine if they launch async co-routines of their own,
     *   but they're responsible for scoping/cancellation and so forth.
     *
     * NOTE: Packing Intents inside a Repo is not ideal, but convenient for
     *   the purpose of writing demo code where I won't expose a repo's
     *   internals.
     */
    interface Intent<T> {
        fun reduce(oldState: T): T
    }

    companion object {
        /**
         * DSL function to help build intents from code blocks.
         */
        fun <T> intent(block: T.() -> T) =
            BlockIntent(block)

        /**
         * By delegating work to other models, repositories or services, we
         * end up with situations where we don't need to update our ModelStore
         * state until the delegated work completes.
         *
         * Use the `sideEffect {}` DSL function for those situations.
         */
        fun <T> sideEffect(block: T.() -> Unit) : Intent<T> = object :
            Intent<T> {
            override fun reduce(oldState: T): T = oldState.apply(block)
        }

        class BlockIntent<T>(val block:T.()->T) :
            Intent<T> {
            override fun reduce(oldState: T): T = block(oldState)
        }
    }

    private val scope = MainScope()
    private val intents = Channel<Intent<S>>()
    private val store = ConflatedBroadcastChannel(startingState)

    init {
        // Reduce from MainScope()
        scope.launch { while (isActive) store.offer(intents.receive().reduce(store.value)) }

        // TODO: Remove later, this is just a quick sanity check
        val foo =
            intent<String> { this }
    }

    // Could be called from any coroutine scope/context.
    private suspend fun process(intent: Intent<S>) {
        intents.send(intent)
    }

    fun modelState(): Flow<S> {
        return store.asFlow()
    }

    fun close() {
        intents.close()
        store.close()
        scope.cancel()
    }
}
