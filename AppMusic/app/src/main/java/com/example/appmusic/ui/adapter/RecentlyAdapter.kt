package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class RecentlyAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var list: MutableList<DataItemRecent> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMusic: IclickMusic ?= null
    fun setIclickMusic(iclickMusic:IclickMusic) {
        this.iclickMusic = iclickMusic
    }

     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        list[holder.adapterPosition].apply {
            with(holder.binding) {
                if (imageSong != null) {
                    imMusicSong.setImageBitmap(imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameSong.text = (musicName)
                tvNameSinger.text = (nameSinger)
                tvNameAlbum.text = (nameAlbum)
                imMore.setOnClickListener { iclickMusic!!.clickMenu(holder.adapterPosition) }
            }
        }


        holder.itemView.setOnClickListener { iclickMusic!!.clickItem(holder.adapterPosition) }

    }

    override val layoutIdItem: Int
         get() = R.layout.item_music
    override val sizeItem: Int
         get() = list.size

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}