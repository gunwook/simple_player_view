package com.gunwook.simplemediaplayer.helper

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.BaseMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource


class ExoPlayerHelper : Player.EventListener {

    internal var player : SimpleExoPlayer? = null
    private var mContext : Context? = null
    private lateinit var mCallback : (playbackState : Int) -> Unit

    internal fun initializePlayer(context : Context , callback : (playbackState : Int) -> Unit) : SimpleExoPlayer? {
        mContext = context
        mCallback = callback

        player = ExoPlayerFactory.newSimpleInstance(context)

        player?.addListener(this)
        return player
    }

    internal fun getDuration() : Long {
        return player?.duration ?: 0
    }

    internal fun getCurrentPosition() : Long {
        return player?.getCurrentPosition() ?: 0
    }

    internal fun reload() {
        player?.seekTo(0)
    }

    internal fun move(move : Long) {
        player?.seekTo(move)
    }

    internal fun play(uri : String) {
        player?.run {
            this.stop()
            this.seekTo(0L)

            this.prepare(buildMediaSource(Uri.parse(uri)),  false , false)
            this.playWhenReady = true
        }
    }

    internal fun pause() {
        player?.playWhenReady = false
    }

    internal fun soundInc(){
        player?.run {
            this.volume = this.volume.inc()
        }
    }

    internal fun soundDec(){
        player?.run {
            this.volume = this.volume.dec()
        }
    }

    internal fun resume() {
        player?.playWhenReady = true
    }


    private fun buildMediaSource(uri : Uri) : BaseMediaSource  {
        var userAgent = "simple-exoplayer-helper"

        return if (uri.lastPathSegment.isNullOrEmpty()) {
            ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        }else if (uri.lastPathSegment!!.contains("mp3") || uri.lastPathSegment!!.contains("mp4")) {
            ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else if (uri.lastPathSegment!!.contains("m3u8")) {
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else {
            ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        }
    }

    internal fun releasePlayer() {
        player?.removeListener(this)
        player?.release()
        player = null
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        mCallback(playbackState)
        /*
        1 Player.STATE_IDLE
        2 Player.STATE_BUFFERING
        3 Player.STATE_READY
        4 Player.STATE_ENDED
        */
    }

}