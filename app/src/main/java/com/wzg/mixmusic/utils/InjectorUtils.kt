package com.wzg.mixmusic.utils

import android.content.ComponentName
import android.content.Context
import com.wzg.library.media.MusicService
import com.wzg.library.media.MusicServiceConnection

/**
 * 用于注入MusicService的连接服务到Activity或Fragment中
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


}