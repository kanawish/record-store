package com.kanawish.recordstore.ui.controls

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
import com.kanawish.recordstore.di.bindViewModel
import com.kanawish.recordstore.di.plusViewModel
import com.kanawish.recordstore.ui.foo.BarViewModel
import com.kanawish.recordstore.ui.foo.FooViewModel
import timber.log.Timber
import java.util.UUID

/**
 * An example how to handle DI scoping with duped co-existing
 * instances of a specific fragment type. (ViewPager is a prime
 * example of this in real-world cases.)
 */
class CounterControlsFragment : Fragment(), ViewModelBinding, CustomVMScopeName {
    companion object {
        private const val KEY = "key"

        // TODO: Hook this up!
        fun newInstance(pageKey: Int): CounterControlsFragment {
            return CounterControlsFragment().apply {
                arguments = bundleOf(KEY to pageKey)
            }
        }
    }

    override fun viewModelBindings() =
        bindViewModel<CounterControlsViewModel>()
            .plusViewModel<FooViewModel>()
            .plusViewModel<BarViewModel>()

    override val customVMScopeName: Any = "${this.javaClass.simpleName}${arguments?.get(KEY) ?: ""}"

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
        // TODO: Check if the theory is sound, the hope is this would get overriden on re-assignment, and on newInstance()?
        arguments = bundleOf(KEY to UUID.randomUUID())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.controls_counter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}