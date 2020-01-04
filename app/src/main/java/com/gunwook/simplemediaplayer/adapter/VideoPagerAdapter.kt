package com.gunwook.simplemediaplayer.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gunwook.simplemediaplayer.callback.VideoCallback
import com.gunwook.simplemediaplayer.callback.VideoMainCallback
import com.gunwook.simplemediaplayer.fragment.VideoFragment
import com.gunwook.simplemediaplayer.model.VideoModel


class VideoPagerAdapter(val list : List<VideoModel> , fm : FragmentManager ,val mainView : VideoMainCallback) : FragmentPagerAdapter(fm , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return VideoFragment.newInstance(list.subList(position , position + 1) , mainView)
    }

    override fun getCount(): Int {
        return list.size
    }
}