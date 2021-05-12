package com.wzg.library.media

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import androidx.annotation.IntDef

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/12 5:15 下午
 */
interface MusicSource {

    /**
     * 开始从音乐源加载数据
     */
    suspend fun load()

    /**
     * 在MusicSource准备好后，执行后续操作
     * @param performAction 一个lambda表达式，在*源准备就绪时用布尔参数调用
     * `true`表示源已成功准备，
     * `false`*表示发生错误。
     *
     * @return Boolean
     */
    fun whenReady(performAction: (Boolean) -> Unit): Boolean

    /**
     * 匹配给定条件，返回数据列表
     * @param query String
     * @param extras Bundle
     * @return List<MediaMetadataCompat>
     */
    fun search(query: String, extras: Bundle): List<MediaMetadataCompat>
}

@IntDef(
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
)
@Retention(AnnotationRetention.SOURCE)
annotation class State

/**
 * State indicating the source was created, but no initialization has performed.
 */
const val STATE_CREATED = 1

/**
 * State indicating initialization of the source is in progress.
 */
const val STATE_INITIALIZING = 2

/**
 * State indicating the source has been initialized and is ready to be used.
 */
const val STATE_INITIALIZED = 3

/**
 * State indicating an error has occurred.
 */
const val STATE_ERROR = 4


abstract class AbstractMusicSource : MusicSource {
    @State
    var state: Int = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()


    override fun whenReady(performAction: (Boolean) -> Unit): Boolean =
        when (state) {
            STATE_CREATED, STATE_INITIALIZING -> {
                onReadyListeners += performAction
                false
            }
            else -> {
                performAction(state != STATE_ERROR)
                true
            }
        }

}