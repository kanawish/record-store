package com.kanawish.recordstore.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kanawish.recordstore.R
import com.kanawish.recordstore.di.ViewModelBinding
import com.kanawish.recordstore.di.bindViewModel
import toothpick.ktp.delegate.inject

class RecordEditActivity : AppCompatActivity(), ViewModelBinding {
    override fun viewModelBindings() =
        bindViewModel<ActivityViewModel>()

    private val viewModel: ActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}