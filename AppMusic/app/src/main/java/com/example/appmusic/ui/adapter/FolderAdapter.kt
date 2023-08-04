package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemFolderBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FolderAdapter : BaseBindingAdapter<ItemFolderBinding>() {
     var listFolder: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field=value
        notifyDataSetChanged()
        }

     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemFolderBinding>,
        position: Int
    ) {
        with(holder.binding){
            tvNamefolder.text = listFolder[position].musicFile
            tvTotalSong.text = "5"
        }

        holder.itemView.setOnClickListener { iBaseClickAdapter?.clickItem(holder.adapterPosition) }
    }

    override val layoutIdItem: Int
         get() = R.layout.item_folder
    override val sizeItem: Int
         get() = listFolder.size
}