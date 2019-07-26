package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable

data class CharacterComic(var items: List<ComicItem> = listOf()) : Parcelable {

    constructor(parcel: Parcel) : this(
            items = parcel.createTypedArrayList(ComicItem.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterComic> {
        override fun createFromParcel(parcel: Parcel): CharacterComic {
            return CharacterComic(parcel)
        }

        override fun newArray(size: Int): Array<CharacterComic?> {
            return arrayOfNulls(size)
        }
    }
}