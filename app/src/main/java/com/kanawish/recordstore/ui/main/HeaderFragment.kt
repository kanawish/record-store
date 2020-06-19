package com.kanawish.recordstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.di.bindViewModel
import com.kanawish.recordstore.di.plusViewModel
import com.kanawish.recordstore.ui.controls.CounterControlsViewModel
import timber.log.Timber
import toothpick.ktp.delegate.inject

class HeaderFragment : Fragment(), ViewModelBinding {
    override fun viewModelBindings() =
        bindViewModel<HeaderViewModel>()
            .plusViewModel<CounterControlsViewModel>()

    companion object {
        fun newInstance() = HeaderFragment()
    }

    // NOTE: No need to bind existing "higher scoped" ViewModels
    private val activityViewModel: ActivityViewModel by inject()

    // NOTE: The two ViewModels below are bound on our behalf in TFLC.
    private val headerViewModel: HeaderViewModel by inject()
    private val counterControlsViewModel: CounterControlsViewModel by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.record_header, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        topViewModel = ViewModelProviders.of(this).get(MainTopViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
