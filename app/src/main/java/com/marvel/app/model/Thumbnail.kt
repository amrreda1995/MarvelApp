package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thumbnails")
data class Thumbnail(
        @PrimaryKey(autoGenerate = true) var _ID: Long? = null,
        var extension: String = "",
        var path: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            extension = parcel.readString() ?: "",
            path = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(extension)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Thumbnail> {
        override fun createFromParcel(parcel: Parcel): Thumbnail {
            return Thumbnail(parcel)
        }

        override fun newArray(size: Int): Array<Thumbnail?> {
            return arrayOfNulls(size)
        }
    }
}