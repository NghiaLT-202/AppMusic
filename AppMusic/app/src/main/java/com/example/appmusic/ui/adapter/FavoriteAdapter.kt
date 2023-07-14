package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FavoriteAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var list: MutableList<Music?> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }



    override fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding>, position: Int) {
        if (list[holder.adapterPosition]?.imageSong != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.adapterPosition]?.imageSong)
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.text = list[position]?.musicName
        holder.binding.tvNameSinger.text = list[position]?.nameSinger
        holder.binding.tvNameAlbum.text = list[position]?.nameAlbum
        holder.itemView.setOnClickListener { iclickMusic!!.clickItem(holder.adapterPosition) }
        holder.binding.imMore.setOnClickListener { iclickMusic!!.clickMenu(holder.adapterPosition) }
    }


    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }

    override fun getLayoutIdItem(): Int {
        return R.layout.item_music
    }

    override fun getSizeItem(): Int {
        return list.size
    }
}