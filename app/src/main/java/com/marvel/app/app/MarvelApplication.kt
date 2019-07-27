package com.marvel.app.app

import android.app.Application
import com.marvel.app.di.ApplicationComponent
import com.marvel.app.di.ApplicationModule
import com.marvel.app.di.DaggerApplicationComponent

class MarvelApplication : Application() {

    lateinit var component: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}