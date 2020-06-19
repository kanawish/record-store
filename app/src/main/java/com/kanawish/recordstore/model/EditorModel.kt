package com.kanawish.recordstore.model

import androidx.lifecycle.MutableLiveData
import com.kanawish.recordstore.model.backend.RecordStoreRestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton @InjectConstructor
class EditorModel(
    initialRecordState: RecordEditorState,
    private val recordStoreRestApi: RecordStoreRestApi
) {
    private val stateStore = MutableLiveData<RecordEditorState>()

    init {
        // At this point, the constructor injected params are available.
        Timber.d("${javaClass.simpleName} init{} [${this}]")
        stateStore.value = initialRecordState
    }

    fun loadRecordState() {

        fun retrofitSuccess(record:RecordState) {
            (stateStore.value as? RecordEditorState.Loading)?.loaded(record)
        }

        fun retrofitError(throwable:Throwable) {
            (stateStore.value as? RecordEditorState.Loading)?.error(throwable)
        }

        (stateStore.value as? RecordEditorState.Editing)?.apply {
            val disposable = recordStoreRestApi.getRecord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::retrofitSuccess, ::retrofitError)

            stateStore.value = this.load(disposable)
        }

    }

    fun saveRecordState() {
        // ...
    }

    fun editRecordState(recordEdit: RecordState.() -> RecordState) {
        (stateStore.value as? RecordEditorState.Editing)?.apply {
            stateStore.value = edit(recordEdit)
        }
    }

}