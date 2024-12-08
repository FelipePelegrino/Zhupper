package com.gmail.devpelegrino.zhupper.di

import com.gmail.devpelegrino.zhupper.ui.trip.request.RequestTripViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RequestTripViewModel(get()) }
}
