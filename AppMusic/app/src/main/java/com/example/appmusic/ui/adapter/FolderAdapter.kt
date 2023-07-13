package com.example.appmusic.ui.adapter

import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemFolderBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FolderAdapter : BaseBindingAdapter<ItemFolderBinding>() {
    private val listFolder: MutableList<Music?> = ArrayList()
    fun setListFolder(listFolder: List<Music?>?) {
        this.listFolder.clear()
        this.listFolder.addAll(listFolder!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemFolderBinding>, position: Int) {
        holder.binding.tvNamefolder.text = listFolder[position].getMusicFile()
        holder.binding.tvTotalSong.text = "5"
        holder.itemView.setOnClickListener { iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_folder
    protected override val sizeItem: Int
        protected get() = listFolder.size
}