package com.gunwook.simplemediaplayer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.Player
import com.gunwook.simplemediaplayer.adapter.MusicDialogAdapter
import com.gunwook.simplemediaplayer.base.BaseDialog
import com.gunwook.simplemediaplayer.callback.MusicCallback
import com.gunwook.simplemediaplayer.helper.ExoPlayerHelper
import com.gunwook.simplemediaplayer.model.MusicModel
import com.gunwook.simplemediaplayer.presenter.BasePresenter
import com.gunwook.simplemediaplayer.presenter.MusicPresenter
import kotlinx.android.synthetic.main.music_dialog.*

class MusicDialog(context : Context , val listOfItems : MutableList<MusicModel> , val title : String) : BaseDialog(context , R.style.FullScreenDialogStyle)  , MusicCallback.View{
    //https://cdn.gunwooklee.com/sound.mp3
    //https://cdn.gunwooklee.com/winter.mp4
    lateinit var presenter : MusicPresenter

    override fun getLayoutId(): Int {
        return R.layout.music_dialog
    }

    override fun initView() {
        presenter = MusicPresenter(context , listOfItems , this)

        musicRecyclerView.layoutManager = LinearLayoutManager(context)
        musicRecyclerView.setHasFixedSize(true)
        musicRecyclerView.adapter = MusicDialogAdapter(context , presenter)

        ivCancel.setOnClickListener {
            dismiss()
        }

        ivControl.setOnClickListener {
            presenter.control()
        }

        ivNext.setOnClickListener {
            presenter.next()
        }

        ivPrevious.setOnClickListener {
            presenter.previous()
        }
        cardView.setOnClickListener {

        }
        parentView.setOnClickListener {
            dismiss()
        }

        setTitle(title)
    }

    override fun onStart() {
        super.onStart()

        if (listOfItems.isNotEmpty()) {
            presenter.play()
        }
    }

    override fun onStop() {
        super.onStop()

        if (listOfItems.isNotEmpty()) {
            presenter.release()
        }
    }

    override fun setTitle(title : String) {
        tvMusic.text = title
    }

    override fun userStatus(status: BasePresenter.MusicStatus) {
        when(status) {
            BasePresenter.MusicStatus.PLAY -> {
                ivControl.setImageResource(R.drawable.exo_icon_pause)
            }
            BasePresenter.MusicStatus.PAUSE -> {
                ivControl.setImageResource(R.drawable.exo_icon_play)

            }
        }
    }

    override fun playerStatus(status: Int) {
        if (status == Player.STATE_READY)
        (musicRecyclerView.adapter as? MusicDialogAdapter)?.notifyItemRangeChanged(0 , listOfItems.size)
    }
}