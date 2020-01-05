package com.gunwook.simplemediaplayer.presenter

import com.gunwook.simplemediaplayer.callback.BaseCallback
import com.gunwook.simplemediaplayer.helper.ExoPlayerHelper
import com.gunwook.simplemediaplayer.model.BaseModel

abstract class BasePresenter<out T : BaseModel>(val listOfItems : List<T>) : BaseCallback {

    val exoPlayer : ExoPlayerHelper by lazy { ExoPlayerHelper() }
    var musicStatus : MusicStatus = MusicStatus.PLAY

    var currentPosition : Int = 0
    override fun reload() {
        exoPlayer.reload()
    }

    override fun pause() {
        exoPlayer.pause()

        stateChange(BasePresenter.MusicStatus.PAUSE)
    }

    override fun soundUp() {
        exoPlayer.soundInc()
    }

    override fun soundDown() {
        exoPlayer.soundDec()
    }

    enum class MusicStatus() {
        PLAY , PAUSE
    }

    override fun play() {
        exoPlayer.play(listOfItems[currentPosition].url)

        stateChange(BasePresenter.MusicStatus.PLAY)
    }

    override fun next() {
        if (listOfItems.size > currentPosition + 1) {
            currentPosition++

            exoPlayer.play(listOfItems[currentPosition].url)

            stateChange(BasePresenter.MusicStatus.PLAY)
        }
    }

    override fun previous() {
        if (0 <= currentPosition + 1) {
            currentPosition--

            exoPlayer.play(listOfItems[currentPosition].url)

            stateChange(BasePresenter.MusicStatus.PAUSE)
        }
    }

    override fun release() {
        exoPlayer.releasePlayer()
    }

    override fun control() {
        when(musicStatus) {
            MusicStatus.PLAY -> {
                exoPlayer.pause()
                stateChange(BasePresenter.MusicStatus.PAUSE)
            }

            MusicStatus.PAUSE -> {
                val position = (exoPlayer.getCurrentPosition() / 1000).toInt()
                val duration = (exoPlayer.getDuration() / 1000).toInt()

                if (position != 0 && position != duration) {
                    exoPlayer.resume()
                }else {
                    play()
                }

                stateChange(BasePresenter.MusicStatus.PLAY)
            }
        }
    }

    override fun stateChange(i: BasePresenter.MusicStatus) {
        when(i){
            BasePresenter.MusicStatus.PAUSE -> {
                musicStatus = BasePresenter.MusicStatus.PAUSE
            }

            BasePresenter.MusicStatus.PLAY -> {
                musicStatus = BasePresenter.MusicStatus.PLAY
            }
        }
    }

    override fun getCurrentTime(): Long {
        return exoPlayer.getCurrentPosition()
    }

    override fun move(progress: Int) {
        exoPlayer.move((progress * 1000).toLong())
    }

    override fun getDuration(): Long {
        return exoPlayer.getDuration()
    }
}