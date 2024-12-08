package com.gmail.devpelegrino.zhupper

import android.app.Application
import com.gmail.devpelegrino.zhupper.di.networkModule
import com.gmail.devpelegrino.zhupper.di.repositoryModule
import com.gmail.devpelegrino.zhupper.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ZhupperApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ZhupperApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}
