package com.marvel.app.repositories

import retrofit2.Retrofit
import javax.inject.Inject

interface CharacterDetailsApi {

}

interface CharacterDetailsRepoInterface {

}

class CharacterDetailsRepo @Inject constructor(private val retrofit: Retrofit) : CharacterDetailsRepoInterface {

}