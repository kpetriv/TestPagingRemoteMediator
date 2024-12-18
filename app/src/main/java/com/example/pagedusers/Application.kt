package com.example.pagedusers

import android.app.Application
import com.example.pagedusers.di.Injection

class App : Application() {

    val injection: Injection by lazy { Injection(application = this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}