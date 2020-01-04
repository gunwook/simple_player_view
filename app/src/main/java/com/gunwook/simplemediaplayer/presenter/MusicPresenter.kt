package com.gunwook.simplemediaplayer.presenter

import android.content.Context
import android.util.Log
import com.gunwook.simplemediaplayer.callback.MusicCallback
import com.gunwook.simplemediaplayer.helper.ExoPlayerHelper
import com.gunwook.simplemediaplayer.model.MusicModel

class MusicPresenter(context : Context , val list : List<MusicModel> , val view : MusicCallback.View) : MusicCallback.Presenter , BasePresenter<MusicModel>(list) {

    init {
        exoPlayer.initializePlayer(context) {
            view.playerStatus(it)
        }
    }

    override fun stateChange(i: MusicStatus) {
        super.stateChange(i)

        when(i){
            MusicStatus.PAUSE -> {
                view.userStatus(musicStatus)
            }

            MusicStatus.PLAY -> {
                view.userStatus(musicStatus)
            }
        }
    }
}