package com.kanawish.recordstore

import android.app.Application
import com.kanawish.recordstore.di.ToothpickActivityLifecycleCallbacks
import com.kanawish.recordstore.di.scope.ApplicationScope
import com.kanawish.recordstore.di.module.ActivityModule
import com.kanawish.recordstore.di.module.ActivityVMModule
import com.kanawish.recordstore.di.module.FragmentModule
import com.kanawish.recordstore.di.module.FragmentVMModule
import com.kanawish.recordstore.model.backend.EditorModelModule
import com.kanawish.recordstore.timber.CrashReportingTree
import timber.log.Timber
import toothpick.InjectConstructor
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject
import toothpick.smoothie.module.SmoothieApplicationModule
import javax.inject.Inject

// TP2
class FooTP2 @Inject constructor(bar:String)

@InjectConstructor
class FooTP3(bar:String)

class MainApp : Application() {

    @Inject lateinit var activityLifecycleCallbacks: ToothpickActivityLifecycleCallbacks
//    private val activityLifecycleCallbacks: ToothpickActivityLifecycleCallbacks by inject()

    // These modules will be installed into their matching scope.
    private val activityVMModule = module {}
    private val activityModule = module {}
    private val fragmentVMModule = module {}
    private val fragmentModule = module {}

    // Top-level module.
    private val appModule = module {
        // We make the modules available for injection
        bind<Module>().withName(ActivityModule::class).toInstance(activityModule)
        bind<Module>().withName(ActivityVMModule::class).toInstance(activityVMModule)
        bind<Module>().withName(FragmentModule::class).toInstance(fragmentModule)
        bind<Module>().withName(FragmentVMModule::class).toInstance(fragmentVMModule)
        // TODO: Other application-scope custom bindings can go here.
    }

    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        } else {
            Timber.plant(CrashReportingTree());
        }

    Toothpick.openRootScope().installModules()
        scope = KTP
            .openScope(ApplicationScope::class.java)
            .installModules(
                    SmoothieApplicationModule(this),
                    appModule,
                    EditorModelModule
            )
        scope.inject(this)

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}