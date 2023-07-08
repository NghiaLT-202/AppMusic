package com.example.appmusic.ui.adapter

import com.example.tfmmusic.R

class MusicAdapter : BaseBindingAdapter<ItemMusicBinding?>() {
    var arrayList: MutableList<Music> = ArrayList<Music>()
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }

    fun setArrayList(arrayList: List<Music>?) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding?>, position: Int) {
        if (arrayList[holder.getAdapterPosition()].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.getAdapterPosition()].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.setText(arrayList[position].getMusicName())
        holder.binding.tvNameSinger.setText(arrayList[position].getNameSinger())
        holder.binding.tvNameAlbum.setText(arrayList[position].getNameAlbum())
        holder.itemView.setOnClickListener { v -> iclickMusic!!.clickItem(position) }
        holder.binding.imMore.setOnClickListener { v -> iclickMusic!!.clickMenu(holder.getAdapterPosition()) }
    }

    protected val layoutIdItem: Int
        protected get() = R.layout.item_music
    protected val sizeItem: Int
        protected get() = arrayList.size

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}