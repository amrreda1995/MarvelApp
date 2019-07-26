package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

data class Comic(
        var id: Int = 0,
        var title: String = "",
        var description: String = "",
        var thumbnail: Thumbnail? = Thumbnail(),
        var images: List<Thumbnail> = listOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            title = parcel.readString() ?: "",
            description = parcel.readString() ?: "",
            thumbnail = parcel.readParcelable(Thumbnail::class.java.classLoader),
            images = parcel.createTypedArrayList(Thumbnail.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(thumbnail, flags)
        parcel.writeTypedList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comic> {
        override fun createFromParcel(parcel: Parcel): Comic {
            return Comic(parcel)
        }

        override fun newArray(size: Int): Array<Comic?> {
            return arrayOfNulls(size)
        }
    }
}

//class ComicViewModel(val comic: Comic) : ViewItemRepresentable {
//
//    val comicImage = "${comic.thumbnail.path}.${comic.thumbnail.extension}"
//
//    override val viewItem: AbstractViewItem<ViewItemRepresentable>
//        get() = ComicViewItem(this)
//}