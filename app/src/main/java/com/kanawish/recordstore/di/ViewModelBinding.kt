package com.kanawish.recordstore.di

import androidx.lifecycle.ViewModel

interface ViewModelBinding {
    val viewModelDependencies:Array<Class<out ViewModel>>
}
