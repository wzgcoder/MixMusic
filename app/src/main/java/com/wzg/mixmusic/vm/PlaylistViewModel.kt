package com.wzg.mixmusic.vm

import androidx.lifecycle.*
import com.wzg.mixmusic.media.MusicServiceConnection
import com.wzg.mixmusic.data.entity.PlayListResult
import com.wzg.mixmusic.data.entity.SearchDataCompat
import com.wzg.mixmusic.data.entity.StandardSongData
import kotlinx.coroutines.launch
import rxhttp.toClass
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp
import timber.log.Timber
import java.util.*

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 4:52 下午
 */
class PlaylistViewModel(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {
    private val _currentPlayList = MutableLiveData<MutableList<StandardSongData>>()
    val currentPlayList: LiveData<MutableList<StandardSongData>> = _currentPlayList

    fun loadPlaylist(playlistId: Long) {
        Timber.i("加载歌单")
        viewModelScope.launch {
            val result = RxHttp.get("/playlist/detail")
                .add("id", playlistId)
                .toClass<PlayListResult>()
                .await()
            val trackIds = mutableListOf<Long>()
            result.playlist.trackIds.forEach {
                trackIds.add(it.id)
            }
            Timber.i(trackIds.toString())

            loadSongDetails(trackIds.toString().run {
                substring(1, length - 1)
            })
        }
    }

    private suspend fun loadSongDetails(ids: String) {
        Timber.i("处理后${ids}")
        val playList = RxHttp.get("/song/detail")
            .add("ids", ids)
            .toStr()
//            .toClass<SearchDataCompat>()
            .await()
        Timber.i(playList.toString())
    }

    class Factory(
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlaylistViewModel(musicServiceConnection) as T
        }
    }
}