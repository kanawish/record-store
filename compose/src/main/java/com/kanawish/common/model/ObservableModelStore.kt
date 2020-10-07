package com.kanawish.common.model

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

open class ObservableModelStore<S>(startingState: S) {
    private val intents = PublishRelay.create<Reducer<S>>()

    private val store = intents
        .observeOn(AndroidSchedulers.mainThread())
        .scan(startingState) { oldState, intent -> intent.reduce(oldState) }
        .replay(1)
        .apply { connect() }

    // Useful for internal diagnostics.
    private val internalDisposable = store.subscribe(::internalLogger, ::crashHandler)
    private fun internalLogger(state:S) = Timber.i("$state")
    private fun crashHandler(throwable: Throwable): Unit = throw throwable

    /**
     * Model will receive intents to be processed via this function.
     *
     * ModelState is immutable. Processed intents will work much like `copy()`
     * and create a new (modified) modelState from an old one.
     */
    fun process(reducer: Reducer<S>) = intents.accept(reducer)

    /**
     * Observable stream of changes to ModelState
     *
     * Every time a modelState is replaced by a new one, this observable will
     * fire.
     *
     * This is what views will subscribe to.
     */
    fun modelState(): Observable<S> = store
}