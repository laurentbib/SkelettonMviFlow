package com.biblublab.skelettonmviflow.common

import android.app.Application
import com.biblublab.skelettonmviflow.common.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SkeletonApplication)
            modules(dbModule,
            networkModule,
                dispatchersModule,
            useCaseModule,
            homeModule,
            )
        }
    }
}