package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FavoriteAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var list: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMusic: IclickMusic? = null
    fun setIClickMusic(iclickMusic: IclickMusic) {
        this.iclickMusic = iclickMusic
    }


     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        with(holder.binding) {
            list[holder.adapterPosition].apply {
                if (imageSong != null) {
                    imMusicSong.setImageBitmap(imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameSong.text = musicName
                tvNameSinger.text = nameSinger
                tvNameAlbum.text = nameAlbum
                imMore.setOnClickListener { iclickMusic?.clickMenu(holder.adapterPosition) }
            }
        }



        holder.itemView.setOnClickListener { iclickMusic?.clickItem(holder.adapterPosition) }

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