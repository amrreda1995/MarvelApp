package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.marvel.app.repositories.CharacterDetailsRepoInterface
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable
import com.recyclerviewbuilder.library.ViewItemsObserver

interface ComicImageInterface {
    fun setComicImage(imageUrl: String)
}

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

class ComicItemViewModel(
        val comicItem: ComicItem,
        private val apiRequestManager: ApiRequestManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface
) : ViewItemRepresentable {

    private lateinit var comicImageInterface: ComicImageInterface

    var comicImage = ""

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = ComicViewItem(this)

    fun getComicsBy(
            resourceUri: String,
            offset: Int = 0
    ) {

        apiRequestManager.execute(
                request = {
                    characterDetailsRepo.getComics(resourceUri, offset)
                },
                onSuccess = { response, headers ->
                    if (response.data.results.isNotEmpty()) {
                        comicImage = getImage(response.data.results[0].thumbnail)

                        comicImageInterface.setComicImage(comicImage)
                    }
                },
                onFailure = {
                }
        )
    }

    private fun getImage(thumbnail: Thumbnail): String {
        return "${thumbnail.path}.${thumbnail.extension}"
    }

    fun setComicImageInterface(comicImageInterface: ComicImageInterface) {
        this.comicImageInterface = comicImageInterface
    }
}