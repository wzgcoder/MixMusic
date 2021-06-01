package com.wzg.mixmusic.ui.playlist

import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import coil.load
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseActivity
import com.wzg.mixmusic.Constant
import com.wzg.mixmusic.R
import com.wzg.mixmusic.data.entity.MediaItemData
import com.wzg.mixmusic.databinding.ActivityPlaylistBinding
import com.wzg.mixmusic.utils.InjectorUtils
import com.wzg.mixmusic.vm.PlaylistViewModel
import timber.log.Timber

class PlaylistActivity : BaseActivity() {
    private val binding by viewbind<ActivityPlaylistBinding>()
    private val playlistViewModel by viewModels<PlaylistViewModel> {
        InjectorUtils.providePlaylistViewModel(this, playlistId)
    }

    private lateinit var playlistId: String

    override fun initViews() {
        playlistId = intent.getStringExtra(Constant.PLAYLIST_ID_KEY).toString()
    }

    override fun initData() {

    }

    override fun initObserver() {
        Timber.i("观察数据")
        playlistViewModel.getPlaylistItems().observe(this) {
            binding.rvPlayList.linear().setup {
                addType<MediaItemData>(R.layout.item_playlist)
                onBind {
                    val model = getModel<MediaItemData>()
                    findView<ImageView>(R.id.iv_logo).load(model.imageUrl)
                    findView<TextView>(R.id.tv_name).text = model.name
                    findView<TextView>(R.id.tv_artis).text = model.artists.toString()
                }
            }.models = it
        }
    }
}
