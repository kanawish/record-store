package com.kanawish.recordstore.timber

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree(){
    // TODO: Get this via injection.
    private val crashLib = FakeCrashLib()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when(priority) {
            Log.DEBUG, Log.VERBOSE -> { }
            else -> {
                crashLib.log(priority,tag,message)
                t?.also { throwable ->
                    when( priority ) {
                        Log.ERROR -> {crashLib.logError(throwable)}
                        Log.WARN -> {crashLib.logWarning(throwable)}
                    }
                }
            }
        }
    }

    class FakeCrashLib {
        fun log(priority: Int, tag: String?, message: String) {
            TODO("not implemented")
        }

        fun logError(throwable: Throwable) {
            TODO("not implemented")
        }

        fun logWarning(throwable: Throwable) {
            TODO("not implemented")
        }
    }
}