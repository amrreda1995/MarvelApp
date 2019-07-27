package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charactersComics")
data class CharacterComics(
        @PrimaryKey(autoGenerate = true) var _ID: Long? = null,
        var items: List<ComicItem> = listOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
            items = parcel.createTypedArrayList(ComicItem.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterComics> {
        override fun createFromParcel(parcel: Parcel): CharacterComics {
            return CharacterComics(parcel)
        }

        override fun newArray(size: Int): Array<CharacterComics?> {
            return arrayOfNulls(size)
        }
    }
}