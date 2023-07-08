package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.singerfragment

@HiltViewModel
class SingerViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository

    init {
        this.musicRepository = musicRepository
    }
}