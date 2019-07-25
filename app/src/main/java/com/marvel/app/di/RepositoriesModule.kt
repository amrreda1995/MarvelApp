package com.marvel.app.di

import com.marvel.app.repositories.CharactersRepo
import com.marvel.app.repositories.CharactersRepoInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindCharactersRepo(repo: CharactersRepo): CharactersRepoInterface
}