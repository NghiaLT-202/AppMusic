package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemAlbumBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class AlbumAdapter : BaseBindingAdapter<ItemAlbumBinding>() {
    var list: MutableList<DataMusic> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override val layoutIdItem: Int
        get() = R.layout.item_album
    override val sizeItem: Int
        get() = list.size


    override fun onBindViewHolderBase(
        holder: BaseHolder<ItemAlbumBinding>,
        position: Int
    ) {
        list[holder.adapterPosition].apply {
            with(holder.binding) {
                imMusicSong.setImageResource((((imageSong ?: R.drawable.ic_apple_music) as Int)))
                tvNameAlbum.text = nameAlbum
                tvNameSinger.text = nameSinger
                root.setOnClickListener { clickItem(holder.adapterPosition, this@apply) }
            }
        }


    }
}