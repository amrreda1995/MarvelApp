package com.marvel.app.repositories

import com.marvel.app.BuildConfig
import com.marvel.app.model.CharactersResponse
import com.marvel.app.utilities.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface CharactersApi {

    @GET(Constants.Endpoints.characters)
    suspend fun getCharacters(
            @Query("ts") ts: String,
            @Query("apikey") apikey: String,
            @Query("hash") hash: String,
            @Query("offset") offset: Int
    ): Response<CharactersResponse>
}

interface CharactersRepoInterface {
    suspend fun getCharacters(offset: Int): Response<CharactersResponse>
}

class CharactersRepo @Inject constructor(private val retrofit: Retrofit) : CharactersRepoInterface {

    override suspend fun getCharacters(offset: Int): Response<CharactersResponse> {
        val api = retrofit.create(CharactersApi::class.java)

        return api.getCharacters(
                BuildConfig.TS,
                BuildConfig.MARVEL_API_PUBLIC_KEY,
                BuildConfig.HASH_KEY,
                offset
        )
    }
}