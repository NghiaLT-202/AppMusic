package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music?> = mutableListOf()
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
        if (arrayList[holder.adapterPosition]?.imageSong != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.adapterPosition]?.imageSong)
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.text = arrayList[position]?.musicName
        holder.binding.tvNameSinger.text = arrayList[position]?.nameSinger
        holder.binding.tvNameAlbum.text = arrayList[position]?.nameAlbum
        holder.itemView.setOnClickListener { v: View? -> iclickMusic!!.clickItem(position) }
        holder.binding.imMore.setOnClickListener { v: View? -> iclickMusic!!.clickMenu(holder.adapterPosition) }
    }


    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }

    override fun getLayoutIdItem(): Int {
        return R.layout.item_music
    }

    override fun getSizeItem(): Int {
        return  arrayList.size
    }
}