package com.marvel.app.model

data class CharactersData(
        var count: Int = 0,
        var total: Int = 0,
        var offset: Int = 0,
        var results: List<Character> = listOf()
)