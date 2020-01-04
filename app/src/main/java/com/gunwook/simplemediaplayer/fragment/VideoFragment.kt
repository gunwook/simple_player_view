package com.gunwook.simplemediaplayer.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.gunwook.simplemediaplayer.FullVideoActivity
import com.gunwook.simplemediaplayer.MusicPopUp
import com.gunwook.simplemediaplayer.R
import com.gunwook.simplemediaplayer.callback.VideoCallback
import com.gunwook.simplemediaplayer.callback.VideoMainCallback
import com.gunwook.simplemediaplayer.databinding.CellListVideoBinding
import com.gunwook.simplemediaplayer.databinding.MusicPlayerBinding
import com.gunwook.simplemediaplayer.ext.timeFormat
import com.gunwook.simplemediaplayer.model.VideoModel
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.VideoPresenter
import com.gunwook.simplemediaplayer.utils.CodeExoPlay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class VideoFragment : Fragment() , VideoCallback.View {

    var listOfItems : ArrayList<VideoModel> = arrayListOf()

    lateinit var mainCallback : VideoMainCallback
    lateinit var view : CellListVideoBinding
    var presenter : VideoPresenter? = null
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(list : List<VideoModel> , mainView : VideoMainCallback) : VideoFragment {
            return VideoFragment().apply {
                this.listOfItems.addAll(list)
                this.mainCallback = mainView
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = DataBindingUtil.inflate(inflater, R.layout.cell_list_video, container, false)

        if(listOfItems.isNotEmpty()) {
            activity?.let {
                presenter = VideoPresenter(it, this, listOfItems)

                presenter?.initalize()
            }

            view.videoView.player = presenter?.exoPlayer?.player

            presenter?.musicStatus = BasePresenter.MusicStatus.PAUSE

            view.tvTitle.text = this.listOfItems[0].title
            view.ivControl.setOnClickListener {
                if(view.ivThumbNail.visibility == View.VISIBLE) view.ivThumbNail.visibility = View.GONE

                presenter?.control()
            }

            view.ivNext.setOnClickListener {
                mainCallback.onNextStep()
            }

            view.ivPrev.setOnClickListener {
                mainCallback.onPrevStep()
            }

            view.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekbar: SeekBar?) {
                    if (presenter?.isPlaying() ?: false) presenter?.move(seekbar?.progress ?: 0)
                }
            })

            view.videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

            view.videoCardView.setOnClickListener {
                Intent(activity , FullVideoActivity::class.java).apply {
                    this.putParcelableArrayListExtra(CodeExoPlay.VIDEO_URLS , listOfItems)
                    startActivity(this)
                }
            }

            Glide.with(this).load(listOfItems[0].thumb).into(view.ivThumbNail)
        }


        return view.root
    }

    override fun userStatus(status: BasePresenter.MusicStatus) {
        when(status) {
            BasePresenter.MusicStatus.PLAY -> {
                view.ivControl.setImageResource(R.drawable.exo_icon_pause)
            }
            BasePresenter.MusicStatus.PAUSE -> {
                view.ivControl.setImageResource(R.drawable.exo_icon_play)
            }
        }
    }

    override fun playerStatus(status: Int) {
        if (status == Player.STATE_READY) {
            view.tvTitle.text = listOfItems[presenter!!.currentPosition].title

            view.seekbar.max = presenter?.getDuration()?.div(1000)?.toInt() ?: 0

            if (compositeDisposable.size() == 0) {
                viewRefresh()

                compositeDisposable.add(
                    Observable.timer(
                        500,
                        TimeUnit.MILLISECONDS
                    ).observeOn(AndroidSchedulers.mainThread()).repeat().subscribe { _ ->
                       viewRefresh()
                    })

            }
        }else if (status == Player.STATE_ENDED){
            compositeDisposable.clear()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewRefresh() {
        val progress = (presenter?.getCurrentTime()?.div(1000))?.toInt() ?: 0
        val duration = (presenter?.getDuration()?.div(1000))?.toInt() ?: 0
        //view.tvTime.setText("${progress.timeFormat()} / ${duration.timeFormat()}")
        view.seekbar.progress = progress
    }

    override fun onStop() {
        super.onStop()

        if (listOfItems.isNotEmpty()) {
            compositeDisposable.clear()
            presenter?.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (listOfItems.isNotEmpty()) {
            compositeDisposable.clear()
            presenter?.release()
        }
    }
}