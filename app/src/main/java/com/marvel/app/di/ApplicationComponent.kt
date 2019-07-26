package com.marvel.app.di

import com.marvel.app.ui.character_details.CharacterDetailsActivity
import com.marvel.app.ui.characters.CharactersActivity
import com.marvel.app.ui.search.SearchActivity
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
    fun inject(target: SearchActivity)
    fun inject(characterDetailsActivity: CharacterDetailsActivity)
}