package com.gunwook.simplemediaplayer.model

import android.os.Parcel
import android.os.Parcelable

data class MusicModel(var _url : String , var title : String) : BaseModel(_url)