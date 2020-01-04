package com.gunwook.simplemediaplayer.model

import android.os.Parcel
import android.os.Parcelable

data class VideoModel(var thumb : String , var _url : String, var title : String) : BaseModel(_url) , Parcelable {

    private constructor(parcel : Parcel?) : this (
        parcel?.readString() ?: "",
        parcel?.readString() ?: "",
        parcel?.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(thumb)
        parcel?.writeString(_url)
        parcel?.writeString(title)

    }

    override fun describeContents(): Int = 0


    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<VideoModel> {
            override fun createFromParcel(p0: Parcel?): VideoModel =   VideoModel(p0)

            override fun newArray(p0: Int): Array<VideoModel?> = arrayOfNulls<VideoModel>(p0)
        }
    }
}