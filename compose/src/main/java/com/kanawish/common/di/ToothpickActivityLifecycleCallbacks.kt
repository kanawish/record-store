package com.kanawish.common.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.kanawish.common.di.scope.ApplicationScope
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.module.SmoothieActivityModule
import toothpick.smoothie.module.SmoothieAndroidXActivityModule
import javax.inject.Singleton

@Singleton
@InjectConstructor
class ToothpickActivityLifecycleCallbacks(
    private val fragmentLifecycleCallbacks: ToothpickFragmentLifecycleCallbacks
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        if (activity is FragmentActivity) {
            KTP.openScope(ApplicationScope::class.java)
                .openSubScope(activity) { scope ->
                    scope.installModules(SmoothieAndroidXActivityModule(activity))
                }
                .closeOnDestroy(activity)
                .inject(activity)

            activity
                .supportFragmentManager
                .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        } else {
            // TODO: Actually investigate if this branch really ever executes.
            KTP.openScope(ApplicationScope::class.java)
                .openSubScope(activity) { scope ->
                    scope.installModules(SmoothieActivityModule(activity))
                }
                .inject(activity)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        // TODO: Actually investigate if this branch really ever executes.
        if (activity !is FragmentActivity) {
            KTP.closeScope(activity)
        }
    }

    // Unused, moved to bottom of class for readability.
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

}
