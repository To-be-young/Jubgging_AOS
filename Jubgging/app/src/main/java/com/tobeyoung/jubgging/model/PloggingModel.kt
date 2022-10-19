package com.tobeyoung.jubgging.model

import android.os.Parcel
import android.os.Parcelable

data class PloggingModel (
    var latitude:Double = 0.0,
    var longitude:Double =0.0,
    var time:String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PloggingModel> {
        override fun createFromParcel(parcel: Parcel): PloggingModel {
            return PloggingModel(parcel)
        }

        override fun newArray(size: Int): Array<PloggingModel?> {
            return arrayOfNulls(size)
        }
    }
}
