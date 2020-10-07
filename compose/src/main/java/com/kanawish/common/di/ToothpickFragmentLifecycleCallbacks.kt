package com.kanawish.common.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kanawish.common.di.scope.ApplicationScope
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import javax.inject.Singleton

@Singleton
@InjectConstructor
class ToothpickFragmentLifecycleCallbacks() : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
        KTP.openScopes(ApplicationScope::class.java).inject(fragment)
    }
}
