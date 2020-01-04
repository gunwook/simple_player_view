package com.gunwook.simplemediaplayer.callback

import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.MusicPresenter

interface VideoCallback {

    interface View {
        fun userStatus(status : BasePresenter.MusicStatus)
        fun playerStatus(status : Int)
    }

    interface Presenter {
    }
}