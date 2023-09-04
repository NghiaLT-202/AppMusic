package com.example.appmusic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemMusicBinding

class MusicAdapter : ListAdapter<DataMusic, MusicAdapter.MusicHolder>(TaskDiffCallback()) {
    var clickItem: (position: Int, music: DataMusic) -> Unit = { _, _ -> }
    var clickMenu: (position: Int, music: DataMusic) -> Unit = { _, _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder(
            ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition), clickMenu, clickItem)
    }

    class MusicHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(
            dataMusic: DataMusic,
            clickMenu: (position: Int, music: DataMusic) -> Unit,
            clickItem: (position: Int, music: DataMusic) -> Unit,
        ) {
            with(binding) {
                if (dataMusic.imageSong != null) {
                    imMusicSong.setImageBitmap(dataMusic.imageSong)
                } else {
                    imMusicSong.setImageResource(R.drawable.ic_apple_music)
                }
                tvNameSong.text = dataMusic.musicName
                tvNameSinger.text = dataMusic.nameSinger
                tvNameAlbum.text = dataMusic.nameAlbum
                imMore.setOnClickListener { clickMenu(adapterPosition, dataMusic) }
            }
            itemView.setOnClickListener { clickItem(adapterPosition, dataMusic) }
        }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<DataMusic>() {
        override fun areItemsTheSame(oldItem: DataMusic, newItem: DataMusic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataMusic, newItem: DataMusic): Boolean {
            return oldItem.checkFavorite == newItem.checkFavorite
                    && oldItem.musicFile == newItem.musicFile
                    && oldItem.musicName == newItem.musicName
                    && oldItem.nameSinger == newItem.nameAlbum
                    && oldItem.namePlayList == newItem.namePlayList
                    && oldItem.date == newItem.date
                    && oldItem.imageSong?.equals(newItem.imageSong) == true
        }

    }
}