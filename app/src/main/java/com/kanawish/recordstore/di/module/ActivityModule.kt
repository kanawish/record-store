package com.kanawish.recordstore.di.module

import javax.inject.Qualifier

/**
 * Our ToothpickActivityLifecycleCallbacks and ToothpickFragmentLifecycleCallbacks
 * expect a module qualified with @ScopeModule and @***Scope annotations.
 *
 * TODO: Validate this works.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityModule
