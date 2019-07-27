package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

data class ComicItem(
        var name: String = "",
        @SerializedName("resourceURI")
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

@Entity(tableName = "comics")
class ComicItemViewModel(
        @PrimaryKey(autoGenerate = true) var _ID: Long = 0,
        var id: Int = 0,
        var characterId: Int = 0,
        val comicName: String = "",
        var comicImage: String = "",
        var comicsType: String = ""
) : ViewItemRepresentable, Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            comicName = parcel.readString() ?: "",
            comicImage = parcel.readString() ?: ""
    )

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = ComicViewItem(this)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(comicName)
        parcel.writeString(comicImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicItemViewModel> {
        override fun createFromParcel(parcel: Parcel): ComicItemViewModel {
            return ComicItemViewModel(parcel)
        }

        override fun newArray(size: Int): Array<ComicItemViewModel?> {
            return arrayOfNulls(size)
        }
    }
}