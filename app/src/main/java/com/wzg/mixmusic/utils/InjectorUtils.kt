package com.wzg.mixmusic.utils

import android.content.ComponentName
import android.content.Context
import com.wzg.mixmusic.media.MusicService
import com.wzg.mixmusic.media.MusicServiceConnection
import com.wzg.mixmusic.vm.PlaylistViewModel

/**
 * 用于注入Activity或Fragment所需类
 *
 * @author wangzhangang
 * @date 2021/5/12 11:01 上午
 */
object InjectorUtils {
    private fun provideMusicServiceConnection(context: Context): MusicServiceConnection {
        return MusicServiceConnection.getInstance(
            context,
            ComponentName(context,MusicService::class.java)
        )
    }
    fun providePlaylistViewModel(context: Context,mediaId:String): PlaylistViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return PlaylistViewModel.Factory(mediaId,musicServiceConnection)
    }

}