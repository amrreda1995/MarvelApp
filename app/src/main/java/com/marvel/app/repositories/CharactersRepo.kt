package com.marvel.app.repositories

import com.marvel.app.model.CharactersResponse
import com.marvel.app.utilities.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

interface CharactersApi {

    @GET("${Constants.Endpoints.characters}${Constants.keysParams}")
    suspend fun getCharacters(): Response<CharactersResponse>
}

interface CharactersRepoInterface {
    suspend fun getCharacters(): Response<CharactersResponse>
}

class CharactersRepo @Inject constructor(private val retrofit: Retrofit): CharactersRepoInterface {

    override suspend fun getCharacters(): Response<CharactersResponse> {
        val api = retrofit.create(CharactersApi::class.java)
        return api.getCharacters()
    }
}