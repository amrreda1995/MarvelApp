package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.marvel.app.R
import com.marvel.app.repositories.local.ComicsLocalRepoInterface
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.AbstractViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class ComicItemViewType(val value: Int) {
    COMIC_ITEM_1(1), COMIC_ITEM_2(2)
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

@Entity(tableName = "comics")
open class ComicItemViewModel(
        @PrimaryKey(autoGenerate = true) var _ID: Long = 0,
        var characterId: Int = 0,
        var resourceURI: String = "",
        var comicName: String = "",
        var comicImage: String = "",
        var comicsType: String = "",
        @Ignore var isLoadingComicImage: Boolean = false,
        @Ignore var comicItemViewType: Int = ComicItemViewType.COMIC_ITEM_1.value,
        @Ignore private val apiRequestManager: ApiRequestManagerInterface? = null,
        @Ignore private val characterDetailsRepo: CharacterDetailsRepoInterface? = null,
        @Ignore private val comicsLocalRepo: ComicsLocalRepoInterface? = null
) : ViewItemRepresentable, Parcelable {

    @Ignore
    private var comicImageSetter: ComicImageSetter? = null

    @Ignore
    val layoutResource = if (comicItemViewType == ComicItemViewType.COMIC_ITEM_1.value) {
        R.layout.item_comic_1
    } else {
        R.layout.item_comic_2
    }

    constructor(parcel: Parcel) : this(
            _ID = parcel.readLong(),
            resourceURI = parcel.readString() ?: "",
            comicName = parcel.readString() ?: "",
            comicImage = parcel.readString() ?: "",
            comicItemViewType = parcel.readInt()
    )

    override val viewItem: AbstractViewItem<ViewItemRepresentable>
        get() = ComicViewItem(this)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_ID)
        parcel.writeString(resourceURI)
        parcel.writeString(comicName)
        parcel.writeString(comicImage)
        parcel.writeInt(comicItemViewType)
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

    fun setComicImageSetter(comicImageSetter: ComicImageSetter) {
        this.comicImageSetter = comicImageSetter
    }

    fun getComicData() {
        if (!isLoadingComicImage) {
            isLoadingComicImage = true

            characterDetailsRepo?.let {
                apiRequestManager?.execute(
                    request = {

                        characterDetailsRepo.getComics(resourceURI)
                    },
                    onSuccess = { response, headers ->

                        isLoadingComicImage = false

                        if (response.data.results.isNotEmpty()) {
                            response.data.results[0].thumbnail?.let {
                                comicImage = "${it.path}.${it.extension}"
                            } ?: run {
                                comicImage = "https://www.wildhareboca.com/wp-content/uploads/sites/310/2018/03/image-not-available.jpg"
                            }
                        }

                        comicImageSetter?.setComicImage(comicImage)

                        updateComicIndDatabase()
                    },
                    onFailure = {
                        isLoadingComicImage = false
                    }
                )
            }
        }
    }

    private fun updateComicIndDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            comicsLocalRepo?.updateComic(resourceURI, comicImage)
        }
    }
}

interface ComicImageSetter {
    fun setComicImage(comicImage: String)
}