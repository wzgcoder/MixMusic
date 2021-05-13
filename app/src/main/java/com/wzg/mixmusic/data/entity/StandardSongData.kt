package com.wzg.mixmusic.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 2:29 下午
 */
@Keep
@Parcelize
data class StandardSongData(
    val source: Int?,//歌曲来源 网易、QQ、本地
    val id: String, //歌曲id
    val name: String, //歌曲名称
    val imageUrl: String,//封面图片
    val artists: ArrayList<StandarArtistsData> //艺术家
) : Parcelable {

    @Keep
    @Parcelize
    data class StandarArtistsData(
        val artistId: String?,//艺术家Id
        val name: String? //艺术家名称
    ) : Parcelable
}
