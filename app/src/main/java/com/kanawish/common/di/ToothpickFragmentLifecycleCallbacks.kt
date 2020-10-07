package com.kanawish.common.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kanawish.common.di.scope.ActivityVMScope
import com.kanawish.common.di.scope.ApplicationScope
import com.kanawish.recordstore.module.FragmentModule
import com.kanawish.recordstore.module.FragmentVMModule
import toothpick.InjectConstructor
import toothpick.config.Module
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.ViewModelUtil
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import javax.inject.Singleton

@Singleton
@InjectConstructor
class ToothpickFragmentLifecycleCallbacks(
    @FragmentVMModule private val fragmentVMModule: Module,
    @FragmentModule private val fragmentModule: Module
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
        val parentScope = KTP.openScopes(
                ApplicationScope::class.java,
                ActivityVMScope::class.java
        )

        val vmScopeName = if (fragment is CustomVMScopeName) {
            fragment.customVMScopeName
        } else {
            fragment.javaClass.simpleName
        }

        parentScope
            .openSubScope(vmScopeName) { scope ->
                if (fragment is ViewModelBinding) {
                    for (clazz in fragment.viewModelDependencies) {
                        ViewModelUtil.installViewModelBinding(scope, fragment, clazz, null)
                    }
                }
                scope.closeOnViewModelCleared(fragment)
                    .installModules(fragmentVMModule)
                if( fragment is CustomVMModule ) {
                    scope.installModules(fragment.customModule())
                }
            }
            .openSubScope(fragment) { scope ->
                scope.installModules(fragmentModule)
            }
            .closeOnDestroy(fragment)
            .inject(fragment)
    }

}