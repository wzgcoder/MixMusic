package com.wzg.mixmusic.vm

import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wzg.mixmusic.data.entity.MediaItemData
import com.wzg.mixmusic.media.MusicServiceConnection
import timber.log.Timber

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 4:52 下午
 */
class PlaylistViewModel(
    private val playlistId: String,
    musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val _playlistItems = MutableLiveData<List<MediaItemData>>()
    fun getPlaylistItems() = _playlistItems

    //订阅回调用来获取音乐数据
    private val subcriptionCallBack = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            val itemList = children.map { child ->
                val subtitle = child.description.subtitle ?: ""
                MediaItemData(
                    source = 0,
                    id = child.mediaId!!,
                    name = child.description.title.toString(),
                    imageUrl = child.description.iconUri.toString(),
                    artists = subtitle.split(",")
                )
            }
            _playlistItems.postValue(itemList)
        }
    }

    private val musicServiceConnection = musicServiceConnection.also {
        it.subscribe(playlistId, subcriptionCallBack)
    }


    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(playlistId, subcriptionCallBack)
    }


    class Factory(
        private val mediaId: String,
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlaylistViewModel(mediaId, musicServiceConnection) as T
        }
    }
}