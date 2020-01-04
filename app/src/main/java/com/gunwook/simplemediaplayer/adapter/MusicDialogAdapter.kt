package com.gunwook.simplemediaplayer.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunwook.simplemediaplayer.R
import com.gunwook.simplemediaplayer.helper.ExoPlayerHelper
import com.gunwook.simplemediaplayer.model.MusicModel
import com.gunwook.simplemediaplayer.presenter.MusicPresenter
import com.gunwook.simplemediaplayer.viewholder.MusicViewHolder

class MusicDialogAdapter(val context : Context , val presenter : MusicPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MusicViewHolder(context , R.layout.cell_list_music , parent,presenter)
    }

    override fun getItemCount(): Int {
        return presenter.listOfItems.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MusicViewHolder)?.onBindViewHolder(presenter.listOfItems[position])
    }
}