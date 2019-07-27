package com.marvel.app.repositories.remote

import com.marvel.app.BuildConfig
import com.marvel.app.model.ComicsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

interface CharacterDetailsApi {

    @GET
    suspend fun getComics(
            @Url resourceUri: String,
            @Query("ts") ts: String,
            @Query("apikey") apikey: String,
            @Query("hash") hash: String
    ): Response<ComicsResponse>
}

interface CharacterDetailsRepoInterface {

    suspend fun getComics(@Url resourceUri: String): Response<ComicsResponse>
}

class CharacterDetailsRepo @Inject constructor(private val retrofit: Retrofit) :
    CharacterDetailsRepoInterface {

    override suspend fun getComics(resourceUri: String): Response<ComicsResponse> {
        val api = retrofit.create(CharacterDetailsApi::class.java)

        return api.getComics(
                resourceUri,
                BuildConfig.TS,
                BuildConfig.MARVEL_API_PUBLIC_KEY,
                BuildConfig.HASH_KEY
        )
    }
}