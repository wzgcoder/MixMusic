package com.wzg.mixmusic.media

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.drake.net.utils.scopeNet
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.wzg.mixmusic.R
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

    private lateinit var playbackState: PlaybackStateCompat

    protected lateinit var mediaSession: MediaSessionCompat

    //连接MediaSession到播放器
    protected lateinit var mediaSessionConnector: MediaSessionConnector
    private var currentPlaylistItems: List<MediaMetadataCompat> = emptyList()

    private val mixAudioAttributes = AudioAttributes.Builder()
        .setContentType(C.CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()
    private val playerListener = PlayerEventListener()
    private val exoPlayer: ExoPlayer by lazy {
        SimpleExoPlayer.Builder(this).build().apply {
            setAudioAttributes(mixAudioAttributes, true)
            //从耳机切换到外放时 暂停
            setHandleAudioBecomingNoisy(true)
            addListener(playerListener)
        }
    }

    private val dataSourceFactory: DefaultDataSourceFactory by lazy {
        //解决Url重定向问题（302）
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            Util.getUserAgent(this, MIX_USER_AGENT),
            null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        );
        DefaultDataSourceFactory(
            this,
            null,
            httpDataSourceFactory
        )
    }


    override fun onCreate() {
        super.onCreate()
        //创建媒体会话
        mediaSession = MediaSessionCompat(this, LOG_TAG).apply {
            isActive = true
        }

        sessionToken = mediaSession.sessionToken

        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(MixPlaybackPreparer())
        mediaSessionConnector.setPlayer(exoPlayer)
    }

    private inner class MixPlaybackPreparer : MediaSessionConnector.PlaybackPreparer {
        override fun onCommand(
            player: Player,
            controlDispatcher: ControlDispatcher,
            command: String,
            extras: Bundle?,
            cb: ResultReceiver?
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_URI

        override fun onPrepare(playWhenReady: Boolean) {

        }

        /**
         * 通过id播放媒体资源
         * 这个方法对应 [MediaControllerCompat.TransportControls.playFromMediaId]
         * @param mediaId String
         * @param playWhenReady Boolean
         * @param extras Bundle?
         */
        override fun onPrepareFromMediaId(
            mediaId: String,
            playWhenReady: Boolean,
            extras: Bundle?
        ) {
            Timber.i("准备播放音乐---通过ID")

        }

        override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) {
            exoPlayer.apply {
                play()
                this.playWhenReady = playWhenReady
                stop()
                clearMediaItems()
                val itemSource =
                    ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                setMediaSource(itemSource)
                prepare()
                seekTo(C.TIME_UNSET)
            }
        }


        private fun preparePlaylist(
            mediadataList: List<MediaMetadataCompat>,
            itemToPlay: MediaMetadataCompat?,
            playWhenReady: Boolean,
            playbackStartPositionMs: Long
        ) {
            val initialWindowIndex =
                if (itemToPlay == null) 0 else mediadataList.indexOf(itemToPlay)
            exoPlayer.apply {
                this.playWhenReady = playWhenReady
                stop()
                clearMediaItems()
                val mediaSource = mediadataList.toMediaSource(dataSourceFactory)
                setMediaSource(mediaSource)
                prepare()
                seekTo(initialWindowIndex, playbackStartPositionMs)
            }
        }
    }


    private inner class PlayerEventListener : Player.Listener {

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            Timber.e("播放器准备状况----${playWhenReady}")
        }

        override fun onPlaybackStateChanged(state: Int) {
            Timber.e("播放状态该改变----${state}")
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            var message = R.string.unknown_error
            when (error.type) {
                ExoPlaybackException.TYPE_SOURCE -> {
                    message = R.string.unknown_error
                }
            }
            Timber.e("播放错误----${error.message}")
        }

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
            val playlistData = RxHttp.get("/playlist/detail")
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
            val playlistSongs = RxHttp.get("/song/detail")
                .add("ids", ids)
                .toClass<SearchDataCompat>()
                .await()
            //转换成[MediaBrowserCompat.MediaItem]
            val mediaItems = playlistSongs.songs.map { song ->
                var artistNames = song.ar.map { artistItem ->
                    artistItem.name
                }.toString()
                artistNames = artistNames.substring(1, artistNames.length - 1)
                val playlistMetadata = MediaMetadataCompat.Builder().apply {
                    id = song.id
                    title = song.name
                    artist = artistNames
                    albumArtUri = song.al.picUrl
                    flag = MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
                }.build()
                MediaBrowserCompat.MediaItem(playlistMetadata.description, playlistMetadata.flag)
            }
            result.sendResult(mediaItems)
            Timber.i(playlistSongs.toString())
        }
    }
}

private const val MIX_USER_AGENT = "mix.music"
private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
private const val LOG_TAG = "MusicService"