package com.example.appmusic.ui.main.home.recentlyfragment

import com.example.appmusic.data.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentMyMusicVM
@Inject constructor(var musicRepository: MusicRepository) : BaseViewModel() {
    fun deleteRecentMusic() {
        musicRepository.deleteRecentMusic()
    }
}