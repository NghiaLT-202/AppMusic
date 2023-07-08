package com.example.appmusic.ui.adapter

import android.view.View
import com.example.tfmmusic.R

class FolderAdapter : BaseBindingAdapter<ItemFolderBinding?>() {
    private val listFolder: MutableList<Music> = ArrayList<Music>()
    fun setListFolder(listFolder: List<Music>?) {
        this.listFolder.clear()
        this.listFolder.addAll(listFolder!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemFolderBinding?>, position: Int) {
        holder.binding.tvNamefolder.setText(listFolder[position].getMusicFile())
        holder.binding.tvTotalSong.setText("5")
        holder.itemView.setOnClickListener(View.OnClickListener { iBaseClickAdapter.clickItem(holder.getAdapterPosition()) })
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_folder
    }

    protected fun getSizeItem(): Int {
        return listFolder.size
    }
}