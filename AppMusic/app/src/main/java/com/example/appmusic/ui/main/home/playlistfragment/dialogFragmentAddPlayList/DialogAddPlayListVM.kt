package com.example.appmusic.ui.main.home.playlistfragment.dialogFragmentAddPlayList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DialogAddPlayListVM @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
     var listDataPlayList = MutableLiveData<MutableList<DataPlayList>>()
    val allPlayList: Unit
        get() {
            viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
                _: CoroutineContext, throwable: Throwable
            ) {
                run {
                    Timber.e(throwable)
                }
            })) {
                listDataPlayList.postValue(musicRepository.getAllPlayList())
            }
        }


}