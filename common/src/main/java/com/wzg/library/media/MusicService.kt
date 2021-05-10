package com.wzg.library.media

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/10 10:41 上午
 */
class MusicService : MediaBrowserServiceCompat() {
    /*正在进行的媒体播放会话,它提供了各种机制来控制播放，接收状态更新以及检索有关当前媒体的元数据。*/
    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate() {
        super.onCreate()

        //构建一个 PendingIntent 用于启动UI。
        val sessionActivityPendingIntent =
            packageManager.getLaunchIntentForPackage(packageName).let { sessionIntent ->
                PendingIntent.getActivity(this, 0, sessionIntent, 0)
            }

        //创建一个新的 MediaSession。
        mediaSession = MediaSessionCompat(this, "MusioService")
            .apply {
                setSessionActivity(sessionActivityPendingIntent)
                //让会话处于活动状态，准备接受命令
                isActive = true
            }

    }

    /**
     * 返回"root"媒体id，用于获取用于 浏览/播放 的媒体列表。
     */
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }


    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }

}