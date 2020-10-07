package com.kanawish.common.di

import toothpick.config.Module

/**
 * If an activity or fragment implements CustomModule, the
 * lifecycle callbacks will install it in the activity or fragment
 * lifecycle-bound scope.
 *
 * NOTE: This should be used sparingly / only when necessary.
 */
interface CustomVMModule {
    fun customModule():Module
}