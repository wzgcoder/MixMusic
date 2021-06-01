package com.wzg.mixmusic.media

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.drake.net.Get
import com.drake.net.utils.scopeNet
import com.wzg.mixmusic.data.entity.PlayListResult
import com.wzg.mixmusic.data.entity.SearchDataCompat
import rxhttp.toClass
import rxhttp.wrapper.param.RxHttp
import timber.log.Timber

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/1 4:31 下午
 */
class MusicService : MediaBrowserServiceCompat() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var playbackState: PlaybackStateCompat


    override fun onCreate() {
        super.onCreate()
        //创建媒体会话
        mediaSession = MediaSessionCompat(this, LOG_TAG).apply {
            //在会话上设置此标志，表示它可以处理媒体按钮事件。
            // 已弃用此标志不再使用。
            // 所有媒体会话都将处理媒体按钮事件。为了向后兼容，将始终设置此标志。
//            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
//                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
//            )
            playbackState = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                ).build()
            setPlaybackState(playbackState)
            setCallback(MediaSessionCallBack())
            setSessionToken(sessionToken)
        }

    }

    private inner class MediaSessionCallBack : MediaSessionCompat.Callback() {
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        result.detach()
        scopeNet {
            //获取歌单ids
            val playlistData =  RxHttp.get("/playlist/detail")
                .add("id", parentId)
                .toClass<PlayListResult>()
                .await()
            val trackIds = mutableListOf<Long>()
            playlistData.playlist.trackIds.forEach {
                trackIds.add(it.id)
            }
            //获取歌单所有歌曲url
            val ids = trackIds.toString().run {
                substring(1, length - 1)
            }
            val playlistSongs =  RxHttp.get("/song/detail")
                .add("ids", ids)
                .toClass<SearchDataCompat>()
                .await()
            //转换成[MediaBrowserCompat.MediaItem]
            val mediaItems = playlistSongs.songs.map {
                val playlistMetadata = MediaMetadataCompat.Builder().apply {
                    id = it.id.toString()
                    title = it.name
                    artist = it.ar.map { artistItem ->
                        artistItem.name
                    }.toString()
                    flag = MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
                }.build()
                MediaBrowserCompat.MediaItem(playlistMetadata.description, playlistMetadata.flag)
            }
            result.sendResult(mediaItems)
            Timber.i(playlistSongs.toString())
        }
    }
}


private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
private const val LOG_TAG = "MusicService"