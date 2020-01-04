package com.gunwook.simplemediaplayer.callback

import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.MusicPresenter

interface BaseCallback {
    fun play()
    fun reload()
    fun next()
    fun previous()
    fun release()
    fun control()
    fun soundUp()
    fun soundDown()
    fun pause()
    fun getCurrentTime() : Long
    fun move(progress : Int)
    fun getDuration() : Long
    fun stateChange(i : BasePresenter.MusicStatus)

}