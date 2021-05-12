package com.wzg.library.media

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/10 10:41 上午
 */
class MusicService : MediaBrowserServiceCompat() {
    /* 媒体会话 */
    private lateinit var mediaSession: MediaSessionCompat

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


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
     * 此方法只在服务连接的时候调用
     * 返回一个rootId不为空的BrowserRoot则表示客户端可以连接服务，也可以浏览其媒体资源
     * 如果返回null则表示客户端不能流量媒体资源
     */
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? =
        BrowserRoot(MIX_BROWSABLE_ROOT, null)


    /**
     * 与客户端通信：
     * 当客户端发送订阅后，这里判断不同的 parentId (就是MediaId)返回数据不同的数据。
     */
    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        
    }

}

/*
 * (Media) Session events
 */
const val NETWORK_FAILURE = "com.example.android.uamp.media.session.NETWORK_FAILURE"