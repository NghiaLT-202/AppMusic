package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var listDataMusic: MutableList<DataMusic> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        listDataMusic[holder.adapterPosition].apply {
            with(holder.binding){
                if (imageSong != null) {
                    imMusicSong.setImageBitmap(imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameSong.text = musicName
                tvNameSinger.text = nameSinger
                tvNameAlbum.text = nameAlbum
                imMore.setOnClickListener {  clickMenu(holder.adapterPosition,this@apply) }
            }
            holder.itemView.setOnClickListener { clickItem(position, this@apply) }
        }


    }

    override val layoutIdItem: Int
        get() = R.layout.item_music
    override val sizeItem: Int
        get() = listDataMusic.size



}