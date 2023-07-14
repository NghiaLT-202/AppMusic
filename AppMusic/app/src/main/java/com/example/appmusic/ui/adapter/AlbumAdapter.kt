package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemAlbumBinding
import com.example.appmusic.ui.base.BaseBindingAdapter


class AlbumAdapter : BaseBindingAdapter<ItemAlbumBinding>() {
    var list: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemAlbumBinding>, position: Int) {
        if (list[holder.adapterPosition].imageSong != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.adapterPosition].imageSong)
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.text = list[position].nameAlbum
        holder.binding.tvNameSinger.text = list[position].nameSinger
        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    override fun getLayoutIdItem(): Int {
        return R.layout.item_album
    }

    override fun getSizeItem(): Int {
        return list.size
    }
}