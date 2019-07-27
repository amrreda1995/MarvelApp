package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

@Entity(tableName = "comicsItems")
data class ComicItem(
        @PrimaryKey(autoGenerate = true) var _ID: Long? = null,
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

class ComicItemViewModel(
        val comicItem: ComicItem, var comicImage: String = ""
) : ViewItemRepresentable, Parcelable {

    constructor(parcel: Parcel) : this(
            comicItem = parcel.readParcelable(ComicItem::class.java.classLoader),
            comicImage = parcel.readString() ?: ""
    )

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = ComicViewItem(this)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(comicItem, flags)
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