package com.marvel.app.model

data class ComicsResponse(
        var data: ComicsData = ComicsData(),
        var code: Int = 0
) {
    data class ComicsData(
            var results: List<Comic> = listOf()
    ) {
        data class Comic(
                var id: Int = 0,
                var title: String = "",
                var description: String = "",
                var thumbnail: Thumbnail = Thumbnail()
        )
    }
}