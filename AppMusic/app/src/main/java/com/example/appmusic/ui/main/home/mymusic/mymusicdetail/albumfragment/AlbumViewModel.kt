package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.albumfragment

@HiltViewModel
class AlbumViewModel @Inject constructor(albumRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository

    init {
        musicRepository = albumRepository
    }
}