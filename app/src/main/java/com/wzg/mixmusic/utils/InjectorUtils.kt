package com.wzg.mixmusic.utils

import android.app.Application
import android.content.ComponentName
import android.content.Context
import com.wzg.mixmusic.media.MusicService
import com.wzg.mixmusic.media.MusicServiceConnection
import com.wzg.mixmusic.vm.NowPlayingFragmentViewModel
import com.wzg.mixmusic.vm.PlayerViewModel
import com.wzg.mixmusic.vm.PlaylistViewModel

/**
 * 用于注入Activity或Fragment所需类,用于解偶
 *
 * @author wangzhangang
 * @date 2021/5/12 11:01 上午
 */
object InjectorUtils {
    private fun provideMusicServiceConnection(context: Context): MusicServiceConnection {
        return MusicServiceConnection.getInstance(
            context,
            ComponentName(context, MusicService::class.java)
        )
    }

    fun providePlaylistViewModel(context: Context, mediaId: String): PlaylistViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return PlaylistViewModel.Factory(mediaId, musicServiceConnection)
    }


    fun provideNowPlayingViewModel(context: Context): NowPlayingFragmentViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return NowPlayingFragmentViewModel.Factory(
            applicationContext as Application,
            musicServiceConnection
        )
    }

    fun providePlayerViewModel(context: Context) : PlayerViewModel.Factory{
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return PlayerViewModel.Factory(musicServiceConnection)
    }

}