package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvel.app.R
import com.marvel.app.reusable.viewitems.CharacterViewItem
import com.marvel.app.reusable.viewitems.CharacterViewItemType
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true) var _ID: Long? = null,
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var thumbnail: Thumbnail = Thumbnail(),
    var urls: List<CharacterUrl> = listOf(),
    var comics: CharacterComics = CharacterComics(),
    var events: CharacterComics = CharacterComics(),
    var series: CharacterComics = CharacterComics(),
    var stories: CharacterComics = CharacterComics()
): Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            name = parcel.readString() ?: "",
            description = parcel.readString() ?: "",
            thumbnail = parcel.readParcelable(Thumbnail::class.java.classLoader),
            urls = parcel.createTypedArrayList(CharacterUrl.CREATOR),
            comics = parcel.readParcelable(CharacterComics::class.java.classLoader),
            events = parcel.readParcelable(CharacterComics::class.java.classLoader),
            series = parcel.readParcelable(CharacterComics::class.java.classLoader),
            stories = parcel.readParcelable(CharacterComics::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeParcelable(thumbnail, flags)
        parcel.writeTypedList(urls)
        parcel.writeParcelable(comics, flags)
        parcel.writeParcelable(events, flags)
        parcel.writeParcelable(series, flags)
        parcel.writeParcelable(stories, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}

class CharacterViewModel(
        val character: Character,
        characterViewItemType: CharacterViewItemType = CharacterViewItemType.VIEW_TYPE_1
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