package com.wzg.mixmusic.ui.activity

import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseActivity
import com.wzg.mixmusic.Constant
import com.wzg.mixmusic.databinding.ActivityPlaylistBinding
import com.wzg.mixmusic.ui.adapter.PlaylistAdapter
import com.wzg.mixmusic.utils.InjectorUtils
import com.wzg.mixmusic.vm.PlayerViewModel
import com.wzg.mixmusic.vm.PlaylistViewModel
import timber.log.Timber

class PlaylistActivity : BaseActivity() {
    private val binding by viewbind<ActivityPlaylistBinding>()
    private val playlistViewModel by viewModels<PlaylistViewModel> {
        InjectorUtils.providePlaylistViewModel(this, playlistId)
    }
    private val playerViewModel by viewModels<PlayerViewModel> {
        InjectorUtils.providePlayerViewModel(this)
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
            binding.rvPlayList.apply {
                layoutManager = LinearLayoutManager(this@PlaylistActivity)
                adapter = PlaylistAdapter().apply {
                    setNewInstance(it.toMutableList())
                    setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(
                            adapter: BaseQuickAdapter<*, *>,
                            view: View,
                            position: Int
                        ) {
                            playerViewModel.playMedia(Uri.parse("https://music.163.com/song/media/outer/url?id=${data[position].id}.mp3"))
                        }
                    })
                }
            }
        }
    }

}
