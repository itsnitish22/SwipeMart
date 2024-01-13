package com.nitishsharma.swipemart.application

import android.app.Application
import com.nitishsharma.chatapp.di.networkModule
import com.nitishsharma.swipemart.di.apiModule
import com.nitishsharma.swipemart.di.repositoryModule
import com.nitishsharma.swipemart.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import timber.log.Timber
import org.koin.core.context.startKoin

class FirstProduct : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@FirstProduct)
            modules(
                apiModule,
                networkModule,
                repositoryModule,
                viewModelModules
            )
        }
    }
}