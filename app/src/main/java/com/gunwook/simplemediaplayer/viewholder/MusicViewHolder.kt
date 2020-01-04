package com.gunwook.simplemediaplayer.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.gunwook.simplemediaplayer.R
import com.gunwook.simplemediaplayer.model.MusicModel
import kotlinx.android.synthetic.main.cell_list_music.view.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.gunwook.simplemediaplayer.presenter.MusicPresenter


class MusicViewHolder(val context : Context, @LayoutRes layoutRef : Int, parent : ViewGroup ,val presenter : MusicPresenter) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutRef,parent,false)) {

    val tvMusicPosition = itemView.tvMusicPosition
    val tvMusicTitle = itemView.tvMusicTitle
    val ivSound = itemView.ivSound


    init {
        itemView.isClickable = true

        itemView.setOnClickListener {
            presenter.currentPosition = adapterPosition

            presenter.play()
        }

    }

    fun onBindViewHolder(model : MusicModel) {
        if (adapterPosition % 2 == 0)
        itemView.background = ContextCompat.getDrawable(context , R.drawable.music_select_efefef_bg)
        else
        itemView.background = ContextCompat.getDrawable(context , R.drawable.music_select_aaaaaa_bg)

        if (presenter.currentPosition == adapterPosition) {
            ivSound.visibility = View.VISIBLE
        }else {
            ivSound.visibility = View.GONE
        }

        tvMusicPosition.text = (adapterPosition + 1).toString()
        tvMusicTitle.text = model.title
    }
}