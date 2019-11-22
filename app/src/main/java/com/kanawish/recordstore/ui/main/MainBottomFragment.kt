package com.kanawish.recordstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.ui.duped.DupedViewModel
import timber.log.Timber
import toothpick.ktp.delegate.inject

class MainBottomFragment : Fragment(), ViewModelBinding {
    override val viewModelDependencies: Array<Class<out ViewModel>> = arrayOf(
            DupedViewModel::class.java, MainBottomViewModel::class.java
    )

    companion object {
        fun newInstance() = MainBottomFragment()
    }

    private val activityViewModel: MainActivityViewModel by inject()

    private val mainBottomViewModel: MainBottomViewModel by inject()
    private val dupedViewModel: DupedViewModel by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(MainBottomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
