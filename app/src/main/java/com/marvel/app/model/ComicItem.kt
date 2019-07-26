package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable

data class ComicItem(
        var name: String = "",
        var resourceURI: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            name = parcel.readString() ?: "",
            resourceURI = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(resourceURI)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicItem> {
        override fun createFromParcel(parcel: Parcel): ComicItem {
            return ComicItem(parcel)
        }

        override fun newArray(size: Int): Array<ComicItem?> {
            return arrayOfNulls(size)
        }
    }
}