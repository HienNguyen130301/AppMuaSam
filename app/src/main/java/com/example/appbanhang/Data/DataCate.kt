package com.example.appbanhang.Data

import android.os.Parcel
import android.os.Parcelable

class DataCate(val img: Int, val des: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(img)
        parcel.writeString(des)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataCate> {
        override fun createFromParcel(parcel: Parcel): DataCate {
            return DataCate(parcel)
        }

        override fun newArray(size: Int): Array<DataCate?> {
            return arrayOfNulls(size)
        }
    }
}