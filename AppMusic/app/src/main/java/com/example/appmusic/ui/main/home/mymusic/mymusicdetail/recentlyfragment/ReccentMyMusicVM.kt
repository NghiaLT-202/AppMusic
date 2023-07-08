package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.recentlyfragment

import com.example.tfmmusic.data.repository.MusicRepository

@HiltViewModel
class ReccentMyMusicVM @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    var musicRepository: MusicRepository

    init {
        this.musicRepository = musicRepository
    }

    fun deleteReccentMusic() {
        musicRepository.deleteRecentMusic()
    }
}