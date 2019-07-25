package com.marvel.app.utilities

import com.marvel.app.BuildConfig

class Constants {
    companion object {
        const val baseURL = "https://gateway.marvel.com/v1/public/"
        const val keysParams = "?ts=${BuildConfig.TS}&apikey=${BuildConfig.MARVEL_API_PUBLIC_KEY}&hash=${BuildConfig.HASH_KEY}"
    }

    class Endpoints {
        companion object {
            //end points
            const val characters = "characters"
        }
    }
}