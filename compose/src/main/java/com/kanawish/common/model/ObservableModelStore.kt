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

    fun process(reducer: Reducer<S>) = intents.accept(reducer)

    fun modelState(): Observable<S> = store
}