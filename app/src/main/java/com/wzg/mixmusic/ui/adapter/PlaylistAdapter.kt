package com.wzg.mixmusic.ui.adapter

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wzg.mixmusic.R
import com.wzg.mixmusic.data.entity.MediaItemData

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/2 6:33 下午
 */
class PlaylistAdapter : BaseQuickAdapter<MediaItemData, BaseViewHolder>(R.layout.item_playlist) {
    override fun convert(holder: BaseViewHolder, item: MediaItemData) {
        holder.setText(R.id.tv_name, item.name)
        val artists = item.artists.toString()
        holder.setText(R.id.tv_artis, artists.substring(1, artists.length - 1))
        holder.getView<ImageView>(R.id.iv_logo).load(item.imageUrl)
    }

}