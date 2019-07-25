package com.marvel.app.di

import com.marvel.app.ui.characters.CharactersActivity
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
            ApplicationModule::class,
            RepositoriesModule::class,
            ViewModelModule::class,
            SharedPreferencesModule::class
        ]
)
@Singleton
interface ApplicationComponent {

    fun inject(target: CharactersActivity)
}