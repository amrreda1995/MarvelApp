package com.marvel.app.di

import com.marvel.app.ui.character_details.CharacterDetailsActivity
import com.marvel.app.ui.characters.CharactersActivity
import com.marvel.app.ui.comic_images.ComicsActivity
import com.marvel.app.ui.search.SearchActivity
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
            ApplicationModule::class,
            RepositoriesModule::class,
            ViewModelModule::class
        ]
)
@Singleton
interface ApplicationComponent {

    fun inject(target: CharactersActivity)
    fun inject(target: SearchActivity)
    fun inject(characterDetailsActivity: CharacterDetailsActivity)
    fun inject(comicsActivity: ComicsActivity)
}