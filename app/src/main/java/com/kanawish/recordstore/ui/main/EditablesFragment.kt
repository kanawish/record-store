package com.kanawish.recordstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.di.bindViewModel
import timber.log.Timber
import toothpick.ktp.delegate.inject

class EditablesFragment : Fragment(), ViewModelBinding {
    override fun viewModelBindings()
            = bindViewModel<EditablesViewModel>()

    companion object {
        fun newInstance() = EditablesFragment()
    }

    private val activityViewModel: ActivityViewModel by inject()
    private val editablesViewModel: EditablesViewModel by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.record_editables, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        topViewModel = ViewModelProviders.of(this).get(MainTopViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
