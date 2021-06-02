package com.wzg.mixmusic.vm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wzg.mixmusic.media.MusicServiceConnection

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/2 6:13 下午
 */
class PlayerViewModel(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {


    /**
     * 根据ID播放媒体文件
     * @param meidaId String
     */
    fun playMedia(mediaId: String) {
        musicServiceConnection.transportControls.playFromMediaId(mediaId, null)
    }

    fun playMedia(mediaUrl: Uri) {
        musicServiceConnection.transportControls.playFromUri(mediaUrl, null)
    }


    class Factory(
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlayerViewModel(musicServiceConnection) as T
        }
    }
}