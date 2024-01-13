package com.nitishsharma.swipemart.di

import com.nitishsharma.swipemart.main.home.ProductHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { ProductHomeViewModel() }
}