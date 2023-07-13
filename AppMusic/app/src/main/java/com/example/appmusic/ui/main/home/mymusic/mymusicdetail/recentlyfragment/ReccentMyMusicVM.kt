package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.recentlyfragment

import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReccentMyMusicVM @Inject constructor(var musicRepository: MusicRepository) : BaseViewModel() {
    fun deleteReccentMusic() {
        musicRepository.deleteRecentMusic()
    }
}