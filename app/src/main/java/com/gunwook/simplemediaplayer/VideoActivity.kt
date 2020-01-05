package com.gunwook.simplemediaplayer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
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

    private var prevPosition : Int = 0

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

        videoViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val tempPosition = prevPosition

                mAdapter.fragments[prevPosition].compositeDisposable.clear()
                mAdapter.fragments[prevPosition].presenter?.pause()

                mAdapter.fragments[prevPosition].view.videoView.animate().alpha(0.0f).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                        mAdapter.fragments[tempPosition].view.videoView.visibility = View.GONE
                    }
                });

                mAdapter.fragments[position].view.videoView.animate().alpha(1.0f).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                        mAdapter.fragments[position].view.videoView.visibility = View.VISIBLE
                    }
                });

                prevPosition = position
            }
        })
    }

    override fun onPrevStep() {
        videoViewPager.currentItem = videoViewPager.currentItem - 1
    }

    override fun onNextStep() {
        videoViewPager.currentItem = videoViewPager.currentItem + 1
    }
}