package com.wzg.mixmusic.data.entity


/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 2:56 下午
 */

data class PlayListResult(
    val playlist: PlayListData
) {
    data class PlayListData(
        val id: String?, //歌单id
        val name: String,//歌单名
        val coverImgUrl: String,//歌单封面
        val careatTime: Long, //创建时间
        val userId: String?, //创建者id
        val description: String?, //歌单描述

        val tracks: ArrayList<TrackData>, //歌曲列表
        val trackIds: ArrayList<TrackId> //歌曲Id列表
    ) {
        data class TrackData(
            val name: String,//歌曲名称
            val id: String? //歌曲id
        )

        data class TrackId(
            val id: Long
        )
    }
}


