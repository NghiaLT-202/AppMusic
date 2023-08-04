package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var listMusic: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMusic: IclickMusic? = null
    fun setIClickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }
     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        listMusic[holder.adapterPosition].apply {
            with(holder.binding){
                if (imageSong != null) {
                    imMusicSong.setImageBitmap(imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameSong.text = musicName
                tvNameSinger.text = nameSinger
                tvNameAlbum.text = nameAlbum
                imMore.setOnClickListener {  iclickMusic?.clickMenu(holder.adapterPosition,this@apply) }
            }
            holder.itemView.setOnClickListener { iclickMusic?.clickItem(position,this) }
        }


    }

    override val layoutIdItem: Int
        get() = R.layout.item_music
    override val sizeItem: Int
        get() = listMusic.size


    interface IclickMusic {
        fun clickItem(position: Int,music: Music)
        fun clickMenu(position: Int,music: Music)
    }
}