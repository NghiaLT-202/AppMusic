package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemFolderBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class FolderAdapter : BaseBindingAdapter<ItemFolderBinding>() {
     var listFolder: MutableList<DataMusic> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field=value
        notifyDataSetChanged()
        }


    override fun onBindViewHolderBase(
        holder: BaseHolder<ItemFolderBinding>,
        position: Int
    ) {
         listFolder[position].apply {
             with(holder.binding){
                 tvNamefolder.text = musicFile
                 tvTotalSong.text = App.context.getString(R.string._5)
             }

             holder.itemView.setOnClickListener { clickItem(holder.adapterPosition,this) }
         }

    }

    override val layoutIdItem: Int
         get() = R.layout.item_folder
    override val sizeItem: Int
         get() = listFolder.size
}
