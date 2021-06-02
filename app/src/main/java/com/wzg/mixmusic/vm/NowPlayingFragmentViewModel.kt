package com.wzg.mixmusic.vm

import android.app.Application
import android.content.Context
import android.media.MediaScannerConnection
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wzg.mixmusic.R
import com.wzg.mixmusic.media.EMPTY_PLAYBACK_STATE
import com.wzg.mixmusic.media.MusicServiceConnection

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/2 3:22 下午
 */
class NowPlayingFragmentViewModel(
    private val app: Application,
    mediaServiceConnection: MusicServiceConnection
) : AndroidViewModel(app) {

    private var playbackState: PlaybackStateCompat = EMPTY_PLAYBACK_STATE
    val mediaButtonRes = MutableLiveData<Int>().apply {
        postValue(R.drawable.ic_play)
    }




    class Factory(
        private val app: Application,
        private val mediaServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NowPlayingFragmentViewModel(app, mediaServiceConnection) as T
        }
    }


    data class NowPlayingMetaData(
        val id: String,
        val albumArtUri: String,
        val title: String,
        val artist: String,
        val duration: String
    ) {
        companion object {
            /**
             * 将毫秒转换为分钟和秒的显示方法
             */
            fun timestampToMSS(context: Context, position: Long): String {
                val totalSeconds = Math.floor(position / 1E3).toInt()
                val minutes = totalSeconds / 60
                val remainingSeconds = totalSeconds - (minutes * 60)
                return if (position < 0) context.getString(R.string.duration_unknown)
                else context.getString(R.string.duration_format).format(minutes, remainingSeconds)
            }
        }
    }

}