package com.muhammad.pilltime

import android.app.Application
import com.muhammad.pilltime.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PillTimeApplication : Application(){
    companion object{
        lateinit var INSTANCE : PillTimeApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@PillTimeApplication)
            androidLogger()
            modules(appModule)
        }
    }
}