package com.kanawish.common.model

import com.kanawish.recordstore.tools.infoWorkingIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@ExperimentalCoroutinesApi
open class FlowModelStore<S>(startingState: S) {
    private val scope = MainScope()
    private val intents = Channel<Reducer<S>>()
    private val store = ConflatedBroadcastChannel(startingState)

    fun process(reducer: Reducer<S>) {
        infoWorkingIn("↦ FlowModelStore.process( ${reducer.hashCode()} )")
        intents.offer(reducer) // non-blocking call.
        store.offer(reducer.reduce(store.value))
    }

    @FlowPreview
    fun modelState(): Flow<S> {
        return store.asFlow().distinctUntilChanged()
    }

    fun close() {
        intents.close()
        store.close()
        scope.cancel()
    }
}