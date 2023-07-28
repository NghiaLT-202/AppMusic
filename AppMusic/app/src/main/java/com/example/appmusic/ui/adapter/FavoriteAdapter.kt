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
    private  var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic) {
        this.iclickMusic = iclickMusic
    }


    protected override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        with(holder.binding) {

            if (list[holder.adapterPosition].imageSong != null) {
                imMusicSong.setImageBitmap(list[holder.adapterPosition].imageSong)
            } else {
                imMusicSong.setImageResource(R.drawable.ic_apple_music)
            }
            tvNameSong.text = list[position].musicName
            tvNameSinger.text = list[position].nameSinger
            tvNameAlbum.text = list[position].nameAlbum
            imMore.setOnClickListener { iclickMusic?.clickMenu(holder.adapterPosition) }
        }

        holder.itemView.setOnClickListener { iclickMusic?.clickItem(holder.adapterPosition) }

    }

    override val layoutIdItem: Int
        protected get() = R.layout.item_music
    override val sizeItem: Int
        protected get() = list.size

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}