package com.gunwook.simplemediaplayer.callback

import android.content.Context
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.MusicPresenter

interface MusicCallback {

    interface View {
        fun setTitle(title : String)
        fun userStatus(status : BasePresenter.MusicStatus)
        fun playerStatus(status : Int)
    }

    interface Presenter {

    }

}