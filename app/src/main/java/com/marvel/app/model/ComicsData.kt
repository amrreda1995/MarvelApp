package com.marvel.app.model

import android.os.Parcel
import android.os.Parcelable

data class ComicsData(
        var results: List<Comic> = listOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
            results = parcel.createTypedArrayList(Comic)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicsData> {
        override fun createFromParcel(parcel: Parcel): ComicsData {
            return ComicsData(parcel)
        }

        override fun newArray(size: Int): Array<ComicsData?> {
            return arrayOfNulls(size)
        }
    }
}