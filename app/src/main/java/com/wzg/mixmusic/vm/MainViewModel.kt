package com.wzg.mixmusic.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wzg.mixmusic.media.MusicServiceConnection

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/4/30 4:54 下午
 */
class MainViewModel(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {


    /**
     * 自定义MainViewModel构造
     */
    class Factory(
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(musicServiceConnection) as T
        }
    }
}