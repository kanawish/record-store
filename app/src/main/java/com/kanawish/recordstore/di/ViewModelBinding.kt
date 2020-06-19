package com.kanawish.recordstore.di

import androidx.lifecycle.ViewModel

interface ViewModelBinding {
    fun viewModelBindings():Array<Class<out ViewModel>>
}

/**
 * DSL shorthand for `arrayOf(VM::class.java)`
 *
 * NOTE: If you end up binding more than one ViewModel to a Fragment/Activity,
 *    consider moving those dependencies into the ViewModel or Model layer instead.
 */
inline fun <reified VM : ViewModel> bindViewModel(): Array<Class<out ViewModel>> {
    return arrayOf(VM::class.java)
}

/**
 * Allows you to add more ViewModel bindings to your Activity or Fragment
 *
 * NOTE: If you end up binding more than one ViewModel to a Fragment/Activity,
 *    consider moving those dependencies into the ViewModel or Model layer instead.
 */
inline fun <reified VM : ViewModel> Array<Class<out ViewModel>>.plusViewModel(): Array<Class<out ViewModel>> {
    return this.plusElement(VM::class.java)
}