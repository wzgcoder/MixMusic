package com.wzg.mixmusic.data.entity

import java.util.*

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 7:11 下午
 */
data class SearchDataCompat(
    val songs: ArrayList<CompatSearchSongData>,


    ) {
    data class CompatSearchSongData(
        val id: Long,
        val name: String,
        val al: CompatAlbumData, // val album: CompatAlbumData,
        val ar: ArrayList<CompatArtistData>, // 艺术家
        val fee: Int // 网易云搜索是否是 vip 歌曲
    ) {
        /**
         * 专辑
         */
        data class CompatAlbumData(
            val picUrl: String,
            // val artist: CompatArtistData
        )

        data class CompatArtistData(
            val id: Long,
            val name: String,
            // val img1v1Url: String
        )
    }
}
