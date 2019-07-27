package com.marvel.app.repositories.remote

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

    @GET(Constants.Endpoints.characters)
    suspend fun getCharacters(
            @Query("ts") ts: String,
            @Query("apikey") apikey: String,
            @Query("hash") hash: String,
            @Query("name") searchByName: String,
            @Query("offset") offset: Int
    ): Response<CharactersResponse>
}

interface CharactersRemoteRepoInterface {
    suspend fun getCharacters(searchByName: String? = null, offset: Int): Response<CharactersResponse>
}

class CharactersRemoteRepo @Inject constructor(private val retrofit: Retrofit) :
    CharactersRemoteRepoInterface {

    override suspend fun getCharacters(searchByName: String?, offset: Int): Response<CharactersResponse> {
        val api = retrofit.create(CharactersApi::class.java)

        searchByName?.let {
            return api.getCharacters(
                    BuildConfig.TS,
                    BuildConfig.MARVEL_API_PUBLIC_KEY,
                    BuildConfig.HASH_KEY,
                    it,
                    offset
            )
        } ?: run {
            return api.getCharacters(
                    BuildConfig.TS,
                    BuildConfig.MARVEL_API_PUBLIC_KEY,
                    BuildConfig.HASH_KEY,
                    offset
            )
        }
    }
}