package com.bnp.tiktaktoe

import android.app.Application
import com.bnp.tiktaktoe.di.gameApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(gameApplicationModule)
        }

    }
}

