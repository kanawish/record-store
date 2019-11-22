package com.kanawish.recordstore.di

/**
 * For cases where you have multiple fragments of the same type
 * co-existing in memory, this provides us with a way to make those
 * scopes unique.
 *
 * For example, if you have a ViewPager of FooFragments, using the default
 * "ViewModelFooFragment" scope name for all FooFragment instances will
 * lead to a scope name clash.
 *
 * In that case, you could implement `customScopeName()` to return
 * a generic name with an index suffix, i.e. "ViewModelFooFragment1",
 * "ViewModelFooFragment2", "ViewModelFooFragment3". (Fragment bundle
 * arguments are an easy way to achieve this.)
 *
 * NOTE: This should be used sparingly / only when necessary.
 */
interface CustomVMScopeName {
    val customVMScopeName: Any
}