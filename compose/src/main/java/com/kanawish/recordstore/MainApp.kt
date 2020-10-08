package com.kanawish.recordstore

import android.app.Application
import com.kanawish.common.di.ToothpickActivityLifecycleCallbacks
import com.kanawish.common.di.scope.ApplicationScope
import com.kanawish.recordstore.state.ProductEditorState
import com.kanawish.recordstore.state.ProductEditorState.*
import timber.log.Timber
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject
import toothpick.smoothie.module.SmoothieApplicationModule

/**
 * In this example app, we try to stick to App-scope only, with the idea the VM
 * layer is effectively not needed in an MVI-style app.
 */
class MainApp : Application() {
    private val activityLifecycleCallbacks: ToothpickActivityLifecycleCallbacks by inject()

    private val appModule = module {
        // Initial state provided to app.
        // bind(ProductEditorState::class).toInstance(Closed)

        // TODO: Add modules as needed here.
    }

    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()

        // Logger init
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("%s %d", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        scope = KTP
            .openScope(ApplicationScope::class.java)
            .installModules(SmoothieApplicationModule(this), appModule)
        scope.inject(this)

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}