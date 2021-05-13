package com.wzg.mixmusic.media

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.wzg.mixmusic.media.extensions.id

/**
 * MusicServiceConnection是连接到MusicService的单例。它是MediaBrowser和MediaController类的包装。
 * 它负责：
 * 1.使用MediaBrowser连接到MusicService
 * 2.从MusicService接收回调，以指示medibrowser已成功连接
 * 3.通过订阅MusicService媒体项列表中的更改，允许ViewModel检索专辑和歌曲列表
 * 4.为当前媒体会话创建MediaController，ViewModel可将其用于以下目的：
 *  （1.通过传输控制来控制会话
 *  （2.检索播放状态和元数据更改
 *
 * @author wangzhangang
 * @date 2021/5/12 11:03 上午
 */
class MusicServiceConnection(context: Context, serviceComponent: ComponentName) {
    /*MusicService是否已连接*/
    val isConnected = MutableLiveData<Boolean>()
        .apply { postValue(false) }

    /*正在播放的元数据*/
    val nowPlaying = MutableLiveData<MediaMetadataCompat>().apply {
        postValue(NOTHING_PLAYING)
    }

    /*播放状态*/
    val playBackState = MutableLiveData<PlaybackStateCompat>().apply {
        postValue(EMPTY_PLAYBACK_STATE)
    }

    val networkFailure = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    /*真正控制音乐播放的对象*/
    val transportControls : MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    /*客户端连接回调*/
    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    /*连接MusicService*/
    private val mediaBrowser = MediaBrowserCompat(
        context,
        serviceComponent,
        mediaBrowserConnectionCallback, null
    ).apply { connect() }

    /*媒体控制器*/
    private lateinit var mediaController: MediaControllerCompat


    /**
     * 订阅服务端，用来获取媒体数据
     * @param parentId String
     * @param callback SubscriptionCallback
     */
    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    /**
     * 取消订阅
     * @param parentId String
     * @param callback SubscriptionCallback
     */
    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }







    private inner class MediaBrowserConnectionCallback(val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        /**
         * 连接成功，在[MediaBrowserCompat.connect]之后调用
         */
        override fun onConnected() {
            //为MediaSession 获取MediaContrller
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken)
                .apply { registerCallback(MediaControllerCallBack()) }
        }

        /**
         * 当客户端与媒体浏览器断开是调用
         */
        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
        }

        /**
         * 媒体浏览器连接失败时调用
         */
        override fun onConnectionFailed() {
            isConnected.postValue(false)
        }
    }


    private inner class MediaControllerCallBack : MediaControllerCompat.Callback() {
        /**
         * 播放状态改变回调
         */
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            playBackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
        }


        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            // 当ExoPlayer停止时，我们将收到一个带有“空”元数据的回调。
            //这是一个使用默认值实例化的 元数据对象。媒体ID的默认值
            // 是空的，所以我们假设如果这个值是空，我们不播放
            // 任何东西。
            nowPlaying.postValue(
                if (metadata?.id == null) {
                    NOTHING_PLAYING
                } else {
                    metadata
                }
            )

        }

        //循环模式发生变化
        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
        }

        //随机模式发生变化
        override fun onShuffleModeChanged(shuffleMode: Int) {
            super.onShuffleModeChanged(shuffleMode)
        }

        //session事件
        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            when (event) {
                NETWORK_FAILURE -> networkFailure.postValue(true)

            }
        }

        //session销毁
        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }

    }

    companion object {
        //单例模式
        private var instance: MusicServiceConnection? = null

        fun getInstance(context: Context, serviceComponent: ComponentName) =
            instance ?: synchronized(this) {
                instance ?: MusicServiceConnection(context, serviceComponent)
                    .also { instance = it }
            }
    }
}

val EMPTY_PLAYBACK_STATE: PlaybackStateCompat = PlaybackStateCompat.Builder()
    .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
    .build()

val NOTHING_PLAYING: MediaMetadataCompat = MediaMetadataCompat.Builder()
    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "")
    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0)
    .build()