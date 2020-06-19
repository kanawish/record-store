package com.kanawish.recordstore.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.kanawish.recordstore.di.scope.ActivityVMScope
import com.kanawish.recordstore.di.scope.ApplicationScope
import com.kanawish.recordstore.di.module.ActivityModule
import com.kanawish.recordstore.di.module.ActivityVMModule
import toothpick.InjectConstructor
import toothpick.config.Module
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.module.SmoothieAndroidXActivityModule
import toothpick.smoothie.viewmodel.ViewModelUtil
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import javax.inject.Singleton

@Singleton @InjectConstructor
class ToothpickActivityLifecycleCallbacks(
    @ActivityVMModule val activityVMModule:Module,
    @ActivityModule val activityModule:Module,
    private val fragmentLifecycleCallbacks: ToothpickFragmentLifecycleCallbacks
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        if(activity is FragmentActivity) {
            KTP.openScope(ApplicationScope::class.java)
                .openSubScope(ActivityVMScope::class.java) { scope ->
                    if(activity is ViewModelBinding) {
                        for(clazz in activity.viewModelBindings()) {
                            ViewModelUtil.installViewModelBinding(scope, activity, clazz, null)
                        }
                    }
                    scope.closeOnViewModelCleared(activity)
                        .installModules(activityVMModule)
                    if( activity is CustomVMModule ) {
                        scope.installModules(activity.customModule())
                    }
                }
                .openSubScope(activity) { scope ->
                    scope.installModules(
                            SmoothieAndroidXActivityModule(activity),
                            activityModule
                    )
                }
                .closeOnDestroy(activity)
                .inject(activity)

            activity
                .supportFragmentManager
                .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        } else {
            // NOTE: Would be nice to track if this branch really executes.
            KTP.openScope(ApplicationScope::class.java)
                .openSubScope(activity) { scope -> scope.installModules(activityModule) }
                .inject(activity)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        // NOTE: Would be nice to track if this branch really executes.
        if( activity !is FragmentActivity ) {
            KTP.closeScope(activity)
        }
    }

    // Unused, moved to bottom of class for readability.
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {}
}
