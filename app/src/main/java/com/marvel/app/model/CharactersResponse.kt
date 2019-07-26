package com.marvel.app.model

data class CharactersResponse(
        var data: CharactersData = CharactersData(),
        var code: Int = 0
)