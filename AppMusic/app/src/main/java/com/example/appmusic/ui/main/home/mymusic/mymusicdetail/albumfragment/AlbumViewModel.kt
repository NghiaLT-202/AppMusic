package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.albumfragment

import com.example.appmusic.data.repository.MusicRepositoryimport

com.example.appmusic.ui.base.BaseViewModelimport dagger.hilt.android.lifecycle.HiltViewModelimport javax.inject.Inject
@HiltViewModel
class AlbumViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel()