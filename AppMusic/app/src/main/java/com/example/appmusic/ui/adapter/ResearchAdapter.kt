package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ResearchAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var listMusic: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        listMusic[holder.adapterPosition].apply {
            with(holder.binding) {
                tvNameSong.text = musicName
                imMore.visibility = View.INVISIBLE
                tvNameSinger.text = nameSinger
                if (imageSong != null) {
                    imMusicSong.setImageBitmap(imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameAlbum.text = nameAlbum
            }
        }


        holder.itemView.setOnClickListener {  clickItem(holder.adapterPosition) }
    }

    override val layoutIdItem: Int
         get() = R.layout.item_music
    override val sizeItem: Int
         get() = listMusic.size
}