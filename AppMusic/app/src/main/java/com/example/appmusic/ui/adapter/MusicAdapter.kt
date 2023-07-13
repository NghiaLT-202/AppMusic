package com.example.appmusic.ui.adapter

import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music?> = ArrayList()
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }

    fun setArrayList(arrayList: List<Music?>?) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding>, position: Int) {
        if (arrayList[holder.adapterPosition].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.adapterPosition].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.text = arrayList[position].getMusicName()
        holder.binding.tvNameSinger.text = arrayList[position].getNameSinger()
        holder.binding.tvNameAlbum.text = arrayList[position].getNameAlbum()
        holder.itemView.setOnClickListener { v: View? -> iclickMusic!!.clickItem(position) }
        holder.binding.imMore.setOnClickListener { v: View? -> iclickMusic!!.clickMenu(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_music
    protected override val sizeItem: Int
        protected get() = arrayList.size

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}