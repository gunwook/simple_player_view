package com.gunwook.simplemediaplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gunwook.simplemediaplayer.adapter.VideoPagerAdapter
import com.gunwook.simplemediaplayer.callback.VideoCallback
import com.gunwook.simplemediaplayer.callback.VideoMainCallback
import com.gunwook.simplemediaplayer.ext.dp
import com.gunwook.simplemediaplayer.model.VideoModel
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.utils.CodeExoPlay.VIDEO_URLS
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() , VideoMainCallback {


    lateinit var mAdapter : VideoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)
        initView()
    }

    fun initView() {
        val list = if(intent.hasExtra(VIDEO_URLS)) intent.getParcelableArrayListExtra(VIDEO_URLS) else listOf<VideoModel>()

        mAdapter = VideoPagerAdapter(list , supportFragmentManager , this)

        videoViewPager.run{
            clipToPadding =false
            setPadding(100, 0, 100, 0)
            pageMargin = 50
            offscreenPageLimit = mAdapter.count

            setAdapter(mAdapter)
        }
    }

    override fun onPrevStep() {
        videoViewPager.currentItem = videoViewPager.currentItem - 1
    }

    override fun onNextStep() {
        videoViewPager.currentItem = videoViewPager.currentItem + 1

    }
}