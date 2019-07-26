package com.marvel.app.model

import com.marvel.app.R
import com.marvel.app.ui.characters.viewitems.CharacterViewItem
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

data class CharactersResponse(
    var data: Data = Data(),
    var code: Int = 0
) {
    data class Data(
        var count: Int = 0,
        var total: Int = 0,
        var limit: Int = 0,
        var offset: Int = 0,
        var results: List<Character> = listOf()
    ) {
        data class Character(
            var comics: Comics = Comics(),
            var description: String = "",
            var events: Events = Events(),
            var id: Int = 0,
            var name: String = "",
            var series: Series = Series(),
            var stories: Stories = Stories(),
            var thumbnail: Thumbnail = Thumbnail(),
            var urls: List<Url> = listOf()
        ) {
            data class Events(
                var items: List<Any> = listOf()
            )

            data class Stories(
                var items: List<Any> = listOf()
            )

            data class Series(
                var items: List<Any> = listOf()
            )

            data class Url(
                var type: String = "",
                var url: String = ""
            )

            data class Thumbnail(
                var extension: String = "",
                var path: String = ""
            )

            data class Comics(
                var items: List<Any> = listOf()
            )
        }
    }
}

class CharacterViewModel(val character: CharactersResponse.Data.Character, private val characterViewItemType: CharacterViewItemType): ViewItemRepresentable {

    val layoutResource = if (characterViewItemType == CharacterViewItemType.VIEW_TYPE_1) {
        R.layout.item_character_1
    } else {
        R.layout.item_character_2
    }

    val characterImage = "${character.thumbnail.path}.${character.thumbnail.extension}"

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = CharacterViewItem(this)
}