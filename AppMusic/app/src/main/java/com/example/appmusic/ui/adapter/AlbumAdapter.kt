package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
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
    override val layoutIdItem: Int
        protected get() = R.layout.item_album
    override val sizeItem: Int
        protected get() = list.size


    protected override fun onBindViewHolderBase(
        holder: BaseHolder<ItemAlbumBinding>,
        position: Int
    ) {
        with( holder.binding){
            if (list[holder.adapterPosition].imageSong != null) {
                imMusicSong.setImageBitmap(list[holder.adapterPosition].imageSong)
            } else {
                imMusicSong.setImageResource(R.drawable.ic_apple_music)
            }
            tvNameAlbum.text = list[position].nameAlbum
            tvNameSinger.text = list[position].nameSinger
        }

        holder.itemView.setOnClickListener { iBaseClickAdapter?.clickItem(holder.adapterPosition) }
    }
}