package com.wzg.mixmusic.media.extensions

import android.support.v4.media.MediaMetadataCompat

/**
 * MediaMetadataCompat的扩展
 *
 * @author wangzhangang
 * @date 2021/5/12 2:20 下午
 */
inline val MediaMetadataCompat.id : String?
    get() = getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)