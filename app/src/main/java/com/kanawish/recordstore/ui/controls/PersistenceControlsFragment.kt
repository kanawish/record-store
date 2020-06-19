package com.kanawish.recordstore.ui.controls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.di.bindViewModel
import com.kanawish.recordstore.ui.main.ActivityViewModel
import timber.log.Timber
import toothpick.ktp.delegate.inject

class PersistenceControlsFragment : Fragment(), ViewModelBinding {
    override fun viewModelBindings() =
        bindViewModel<PersistenceControlsViewModel>()

    companion object {
        fun newInstance() = PersistenceControlsFragment()
    }

    private val activityViewModel: ActivityViewModel by inject()

    private val persistenceControlsViewModel: PersistenceControlsViewModel by inject()

    init {
        Timber.d("${javaClass.simpleName} init{} [${this}]")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.controls_persistence, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(MainBottomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
