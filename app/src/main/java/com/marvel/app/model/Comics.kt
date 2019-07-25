package com.marvel.app.model

data class Comics(
    var data: Data = Data(),
    var code: Int = 0
) {
    data class Data(
        var count: Int = 0,
        var limit: Int = 0,
        var offset: Int = 0,
        var results: List<Result> = listOf(),
        var total: Int = 0
    ) {
        data class Result(
            var description: String = "",
            var id: Int = 0,
            var images: List<Image> = listOf(),
            var pageCount: Int = 0,
            var thumbnail: Thumbnail = Thumbnail(),
            var title: String = "",
            var urls: List<Url> = listOf()
        ) {

            data class Image(
                var extension: String = "",
                var path: String = ""
            )

            data class Thumbnail(
                var extension: String = "",
                var path: String = ""
            )

            data class Url(
                var type: String = "",
                var url: String = ""
            )
        }
    }
}