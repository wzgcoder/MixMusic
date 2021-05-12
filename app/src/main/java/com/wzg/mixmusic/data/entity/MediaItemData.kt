package com.wzg.mixmusic.data.entity

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/12 2:30 下午
 */
data class MediaItemData(
    val mediaId: String,
    val title: String,
    val subtitle: String,
    val albumArtUri: Uri,
    val browsable: Boolean,
    var playbackRes: Int
) {

    companion object {
        const val PLAYBACK_RES_CHANGED = 1

        val diffCallbacl = object : DiffUtil.ItemCallback<MediaItemData>() {
            override fun areItemsTheSame(oldItem: MediaItemData, newItem: MediaItemData): Boolean =
                oldItem.mediaId == newItem.mediaId

            override fun areContentsTheSame(
                oldItem: MediaItemData,
                newItem: MediaItemData
            ): Boolean =
                oldItem.mediaId == newItem.mediaId && oldItem.playbackRes == newItem.playbackRes

            override fun getChangePayload(oldItem: MediaItemData, newItem: MediaItemData): Any? =
                if (oldItem.playbackRes != newItem.playbackRes) {
                    PLAYBACK_RES_CHANGED
                } else null

        }
    }


}
