package com.example.businesscard.app

import android.app.Application
import com.example.businesscard.di.databaseModule
import com.example.businesscard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BusinessCard: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BusinessCard)
            modules(
                databaseModule,
                viewModelModule
            )
        }
    }
}