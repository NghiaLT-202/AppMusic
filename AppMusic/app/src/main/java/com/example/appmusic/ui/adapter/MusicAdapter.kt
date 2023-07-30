package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }
    protected override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        with(holder.binding){
            if (arrayList[holder.adapterPosition].imageSong != null) {
                imMusicSong.setImageBitmap(arrayList[holder.adapterPosition].imageSong)
            } else {
                imMusicSong.setImageResource(R.drawable.ic_apple_music)
            }
            tvNameSong.text = arrayList[position].musicName
            tvNameSinger.text = arrayList[position].nameSinger
            tvNameAlbum.text = arrayList[position].nameAlbum
            imMore.setOnClickListener {  iclickMusic?.clickMenu(holder.adapterPosition,arrayList[position]) }
        }
        holder.itemView.setOnClickListener { iclickMusic?.clickItem(position,arrayList[position]) }

    }

    override val layoutIdItem: Int
        get() = R.layout.item_music
    override val sizeItem: Int
        get() = arrayList.size


    interface IclickMusic {
        fun clickItem(position: Int,music: Music)
        fun clickMenu(position: Int,music: Music)
    }
}