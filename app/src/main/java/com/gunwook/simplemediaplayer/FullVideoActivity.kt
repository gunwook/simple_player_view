package com.gunwook.simplemediaplayer

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gunwook.simplemediaplayer.adapter.VideoPagerAdapter
import com.gunwook.simplemediaplayer.callback.VideoCallback
import com.gunwook.simplemediaplayer.callback.VideoMainCallback
import com.gunwook.simplemediaplayer.ext.dp
import com.gunwook.simplemediaplayer.model.VideoModel
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.VideoPresenter
import com.gunwook.simplemediaplayer.utils.CodeExoPlay
import kotlinx.android.synthetic.main.activity_full_video.*
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.cell_list_video.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import kotlinx.android.synthetic.main.exo_controller_view.*


class FullVideoActivity : AppCompatActivity() , VideoCallback.View {

    var presenter : VideoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        val UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        window.decorView.systemUiVisibility = UI_OPTIONS
        setContentView(R.layout.activity_full_video)
        initView()
    }

    fun initView() {
        val list = if(intent.hasExtra(CodeExoPlay.VIDEO_URLS)) intent.getParcelableArrayListExtra(CodeExoPlay.VIDEO_URLS) else listOf<VideoModel>()

        presenter = VideoPresenter(this, this, list)

        presenter?.initalize()

        exoplayer.player = presenter?.exoPlayer?.player
        exo_exit.setOnClickListener {
            finish()
        }
    }

    override fun userStatus(status: BasePresenter.MusicStatus) {

    }

    override fun playerStatus(status: Int) {
    }

    override fun onStart() {
        super.onStart()

        presenter?.play()
    }

    override fun onStop() {
        super.onStop()

        presenter?.pause()

    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.release()

    }
}