package com.kanawish.recordstore.ui.foo

import android.accounts.AccountManager
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.CustomVMScopeName
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.ui.foo.BarViewModel
import com.kanawish.recordstore.ui.foo.FooViewModel
import timber.log.Timber
import toothpick.ktp.delegate.inject
import java.util.UUID
import javax.inject.Inject

/**
 * An example how to handle DI scoping with duped co-existing
 * instances of a specific fragment type. (ViewPager is a prime
 * example of this in real-world cases.)
 */
class FooFragment : Fragment() {

    @Inject lateinit var sharedPrefs : SharedPreferences

    val accountManager: AccountManager by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.foo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}