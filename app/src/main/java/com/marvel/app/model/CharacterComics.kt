package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable

data class CharacterComics(
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