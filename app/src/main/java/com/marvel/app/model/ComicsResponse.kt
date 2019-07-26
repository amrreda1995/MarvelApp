package com.marvel.app.model

data class ComicsResponse(
        var data: ComicsData = ComicsData(),
        var code: Int = 0
)