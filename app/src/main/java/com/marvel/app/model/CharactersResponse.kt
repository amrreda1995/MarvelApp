package com.marvel.app.model

import com.marvel.app.R
import com.marvel.app.ui.characters.viewitems.CharacterViewItem
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

data class CharactersResponse(
        var data: CharactersData = CharactersData(),
        var code: Int = 0
) {
    data class CharactersData(
            var count: Int = 0,
            var total: Int = 0,
            var offset: Int = 0,
            var results: List<Character> = listOf()
    ) {
        data class Character(
                var id: Int = 0,
                var name: String = "",
                var description: String = "",
                var thumbnail: Thumbnail = Thumbnail(),
                var comics: CharacterComic = CharacterComic(),
                var events: CharacterComic = CharacterComic(),
                var series: CharacterComic = CharacterComic(),
                var stories: CharacterComic = CharacterComic()
        )
    }
}

class CharacterViewModel(
        val character: CharactersResponse.CharactersData.Character,
        characterViewItemType: CharacterViewItemType
) : ViewItemRepresentable {

    val layoutResource = if (characterViewItemType == CharacterViewItemType.VIEW_TYPE_1) {
        R.layout.item_character_1
    } else {
        R.layout.item_character_2
    }

    val characterImage = "${character.thumbnail.path}.${character.thumbnail.extension}"

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = CharacterViewItem(this)
}