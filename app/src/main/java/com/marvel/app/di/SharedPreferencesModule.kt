package com.marvel.app.di

import com.marvel.app.utilities.managers.SharedPreferencesManager
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
abstract class SharedPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindSharedPreferences(sharedPreferencesManager: SharedPreferencesManager): SharedPreferencesManagerInterface
}