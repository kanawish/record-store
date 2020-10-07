package com.kanawish.recordstore.ui.duped

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.R
import com.kanawish.common.di.CustomVMScopeName
import com.kanawish.common.di.ViewModelBinding
import com.kanawish.recordstore.ui.foo.BarViewModel
import com.kanawish.recordstore.ui.foo.FooViewModel
import timber.log.Timber
import java.util.UUID

/**
 * An example how to handle DI scoping with duped co-existing
 * instances of a specific fragment type. (ViewPager is a prime
 * example of this in real-world cases.)
 */
class DupedFragment : Fragment(), ViewModelBinding, CustomVMScopeName {
    companion object {
        private const val KEY = "key"
        fun newInstance(pageKey: Int): DupedFragment {
            return DupedFragment().apply {
                arguments = bundleOf(KEY to pageKey)
            }
        }
    }

    override val viewModelDependencies: Array<Class<out ViewModel>> = arrayOf(
            DupedViewModel::class.java,
            FooViewModel::class.java,
            BarViewModel::class.java
    )

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
        return inflater.inflate(R.layout.dupe_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}