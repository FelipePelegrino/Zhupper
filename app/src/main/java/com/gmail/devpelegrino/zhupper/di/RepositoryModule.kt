package com.gmail.devpelegrino.zhupper.di

import com.gmail.devpelegrino.zhupper.network.RideApi
import com.gmail.devpelegrino.zhupper.repository.RideRepository
import com.gmail.devpelegrino.zhupper.repository.RideRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single {
        Gson()
    }

    single {
        Dispatchers.IO
    }

    single<RideApi> { get<Retrofit>().create(RideApi::class.java) }

    single<RideRepository> {
        RideRepositoryImpl(
            ioDispatcher = get(),
            gson = get(),
            rideApi = get()
        )
    }
}
