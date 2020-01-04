package com.gunwook.simplemediaplayer.presenter

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.gunwook.simplemediaplayer.callback.VideoCallback
import com.gunwook.simplemediaplayer.helper.ExoPlayerHelper
import com.gunwook.simplemediaplayer.model.MusicModel
import com.gunwook.simplemediaplayer.model.VideoModel

class VideoPresenter(val mContext : Context, val view : VideoCallback.View , val list : List<VideoModel>) : VideoCallback.Presenter , BasePresenter<VideoModel>(list) {

    fun initalize() {
         exoPlayer.initializePlayer(mContext) {
            view.playerStatus(it)
        }
    }

    fun isPlaying() : Boolean {
        return exoPlayer.player?.isPlaying ?: false
    }

    override fun stateChange(i: MusicStatus) {
        super.stateChange(i)

        when(i){
            BasePresenter.MusicStatus.PAUSE -> {
                view.userStatus(musicStatus)
            }

            BasePresenter.MusicStatus.PLAY -> {
                view.userStatus(musicStatus)
            }
        }
    }
}