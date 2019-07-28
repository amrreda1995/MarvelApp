package com.marvel.app.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return application.resources
    }
}