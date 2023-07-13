package com.example.appmusic.ui.adapter

import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FavoriteAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var list: MutableList<Music?> = ArrayList()
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }

    fun setArrayList(arrayList: List<Music?>?) {
        list.clear()
        list.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding>, position: Int) {
        if (list[holder.adapterPosition].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.adapterPosition].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.text = list[position].getMusicName()
        holder.binding.tvNameSinger.text = list[position].getNameSinger()
        holder.binding.tvNameAlbum.text = list[position].getNameAlbum()
        holder.itemView.setOnClickListener { iclickMusic!!.clickItem(holder.adapterPosition) }
        holder.binding.imMore.setOnClickListener { iclickMusic!!.clickMenu(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_music
    protected override val sizeItem: Int
        protected get() = list.size

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}