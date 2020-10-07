package com.kanawish.recordstore.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.kanawish.recordstore.R
import com.kanawish.common.di.ViewModelBinding
import toothpick.ktp.delegate.inject

class MainActivity : AppCompatActivity(), ViewModelBinding {
    override val viewModelDependencies: Array<Class<out ViewModel>> = arrayOf(MainActivityViewModel::class.java)

    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}