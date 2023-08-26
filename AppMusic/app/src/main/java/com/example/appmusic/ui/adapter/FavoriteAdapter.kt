package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FavoriteAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var list: MutableList<DataMusic> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
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
                imMore.setOnClickListener { clickMenu(holder.adapterPosition,this) }
                holder.itemView.setOnClickListener { clickItem(holder.adapterPosition,this) }

            }

        }




    }

    override val layoutIdItem: Int
         get() = R.layout.item_music
    override val sizeItem: Int
         get() = list.size


}