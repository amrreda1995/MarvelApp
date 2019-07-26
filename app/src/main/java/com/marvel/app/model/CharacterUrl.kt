package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable

data class CharacterUrl(
        var type: String = "",
        var url: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            type = parcel.readString() ?: "",
            url = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterUrl> {
        override fun createFromParcel(parcel: Parcel): CharacterUrl {
            return CharacterUrl(parcel)
        }

        override fun newArray(size: Int): Array<CharacterUrl?> {
            return arrayOfNulls(size)
        }
    }
}