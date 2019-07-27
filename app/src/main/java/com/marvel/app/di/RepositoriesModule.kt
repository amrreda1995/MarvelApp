package com.marvel.app.di

import com.marvel.app.repositories.local.CharactersLocalRepo
import com.marvel.app.repositories.local.CharactersLocalRepoInterface
import com.marvel.app.repositories.remote.CharacterDetailsRepo
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.repositories.remote.CharactersRemoteRepo
import com.marvel.app.repositories.remote.CharactersRemoteRepoInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindCharactersRemoteRepo(repo: CharactersRemoteRepo): CharactersRemoteRepoInterface

    @Binds
    @Singleton
    abstract fun bindCharacterDetailsRepo(repo: CharacterDetailsRepo): CharacterDetailsRepoInterface

    @Binds
    @Singleton
    abstract fun bindCharactersLocalRepo(repo: CharactersLocalRepo): CharactersLocalRepoInterface
}