package com.example.appmusic.ui.adapter

import android.view.View
import com.example.tfmmusic.R

class FavoriteAdapter : BaseBindingAdapter<ItemMusicBinding?>() {
    var list: MutableList<Music> = ArrayList<Music>()
    private var iclickMusic: IclickMusic? = null
    fun setIclickMusic(iclickMusic: IclickMusic?) {
        this.iclickMusic = iclickMusic
    }

    fun setArrayList(arrayList: List<Music>?) {
        list.clear()
        list.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding?>, position: Int) {
        if (list[holder.getAdapterPosition()].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.getAdapterPosition()].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameSong.setText(list[position].getMusicName())
        holder.binding.tvNameSinger.setText(list[position].getNameSinger())
        holder.binding.tvNameAlbum.setText(list[position].getNameAlbum())
        holder.itemView.setOnClickListener(View.OnClickListener { iclickMusic!!.clickItem(holder.getAdapterPosition()) })
        holder.binding.imMore.setOnClickListener(View.OnClickListener {
            iclickMusic!!.clickMenu(
                holder.getAdapterPosition()
            )
        })
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_music
    }

    protected fun getSizeItem(): Int {
        return list.size
    }

    interface IclickMusic {
        fun clickItem(position: Int)
        fun clickMenu(position: Int)
    }
}