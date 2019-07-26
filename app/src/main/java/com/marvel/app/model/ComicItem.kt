package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.marvel.app.repositories.CharacterDetailsRepoInterface
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable
import com.recyclerviewbuilder.library.ViewItemsObserver

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

class ComicItemViewModel(val comicItem: ComicItem) : ViewItemRepresentable {

    var comicImage = ""

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = ComicViewItem(this)
}