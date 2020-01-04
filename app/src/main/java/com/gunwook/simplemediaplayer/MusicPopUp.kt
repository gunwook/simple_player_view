package com.gunwook.simplemediaplayer

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.exoplayer2.Player
import com.gunwook.simplemediaplayer.callback.MusicCallback
import com.gunwook.simplemediaplayer.model.MusicModel
import com.gunwook.simplemediaplayer.presenter.MusicPresenter
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.gunwook.simplemediaplayer.databinding.MusicPlayerBinding
import com.gunwook.simplemediaplayer.ext.timeFormat
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


object MusicPopUp {

    private var mPopUpWindow : PopupWindow? = null
    private var presenter : MusicPresenter? = null
    private val compositeDisposable = CompositeDisposable()

    fun show(context : Context , listOfItems : List<MusicModel>) {
        if (mPopUpWindow != null) return
        if (listOfItems.isNullOrEmpty()) return

        val popupView = DataBindingUtil.inflate<MusicPlayerBinding>(LayoutInflater.from(context) , R.layout.music_player , null , false)

        presenter = MusicPresenter(context , listOfItems , object : MusicCallback.View  {
            override fun setTitle(title: String) {
            }

            override fun userStatus(status: BasePresenter.MusicStatus) {
                when(status) {
                    BasePresenter.MusicStatus.PLAY -> {
                        popupView.ivControl.setImageResource(R.drawable.exo_icon_pause)
                    }
                    BasePresenter.MusicStatus.PAUSE -> {
                        popupView.ivControl.setImageResource(R.drawable.exo_icon_play)
                    }
                }
            }

            override fun playerStatus(status: Int) {
                if (status == Player.STATE_READY) {
                    popupView.tvTitle.text = listOfItems[presenter!!.currentPosition].title

                    popupView.seekbar.max = presenter?.getDuration()?.div(1000)?.toInt() ?: 0

                    if (compositeDisposable.size() == 0) {
                        viewRefresh(popupView)

                        compositeDisposable.add(
                            Observable.timer(
                                500,
                                TimeUnit.MILLISECONDS
                            ).observeOn(AndroidSchedulers.mainThread()).repeat().subscribe { _ ->
                                viewRefresh(popupView)
                            })

                    }
                }else if (status == Player.STATE_ENDED){
                    compositeDisposable.clear()
                }
            }
        })



        popupView.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                presenter?.move(seekbar?.progress ?: 0)
            }
        })

        popupView.ivPrev.setOnClickListener {
            presenter?.previous()
        }

        popupView.ivNext.setOnClickListener {
            presenter?.next()
        }

        popupView.ivPlus.setOnClickListener {
            presenter?.soundUp()
        }

        popupView.ivMinus.setOnClickListener {
            presenter?.soundDown()
        }

        popupView.ivControl.setOnClickListener {
            presenter?.control()
        }

        mPopUpWindow = PopupWindow(popupView.root ,  ConstraintLayout.LayoutParams.MATCH_PARENT , ConstraintLayout.LayoutParams.WRAP_CONTENT )

        mPopUpWindow?.showAtLocation(popupView.root , Gravity.BOTTOM , 0 , 0)

        presenter?.play()
    }

    @SuppressLint("SetTextI18n")
    private fun viewRefresh(popupView : MusicPlayerBinding) {
        val progress = (presenter?.getCurrentTime()?.div(1000))?.toInt() ?: 0
        val duration = (presenter?.getDuration()?.div(1000))?.toInt() ?: 0
        popupView.tvTime.setText("${progress.timeFormat()} / ${duration.timeFormat()}")
        popupView.seekbar.progress = progress
    }

    fun dismiss() {
        compositeDisposable.clear()
        presenter?.release()
        mPopUpWindow?.dismiss()

        presenter = null
        mPopUpWindow = null
    }
}