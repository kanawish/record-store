package com.kanawish.recordstore.model

import io.reactivex.disposables.Disposable

/*
@startuml
hide methods
class RecordEditorState << (D,orchid) >> {
}
@enduml

@startuml
[*] -right-> EDITING
EDITING: recordState
EDITING --> EDITING : edit
EDITING -up-> LOADING : load
LOADING --> EDITING : done
EDITING --> SAVING : save
SAVING --> EDITING : done
@enduml
 */
sealed class RecordEditorState {

    data class Loading(
        val previous:RecordState,
        val disposable:Disposable
    ) : RecordEditorState() {
        fun loaded(record: RecordState) = Editing(record)
        fun error(error: Throwable) = Editing(previous, error)
        fun cancel():Editing {
            disposable.dispose()
            return Editing(previous)
        }
    }

    data class Editing(
        val record: RecordState,
        val error: Throwable? = null
    ) :
        RecordEditorState() {
        fun edit(block: RecordState.() -> RecordState) = copy(record = record.block())
        fun load(disposable: Disposable) = Loading(record, disposable)
        fun save(disposable: Disposable) = Saving(record, disposable)
    }

    data class Saving(
        val saving: RecordState,
        val disposable: Disposable
    ) : RecordEditorState() {
        fun saved(saved: RecordState) = Editing(saved)
        fun error(error: Throwable) = Editing(saving, error)
        fun cancel(): Editing {
            disposable.dispose()
            return Editing(saving)
        }
    }

}