package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.folderfragment

@HiltViewModel
class FolderViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository

    init {
        this.musicRepository = musicRepository
    }
}