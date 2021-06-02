package com.wzg.mixmusic.ui.fragment

import androidx.fragment.app.viewModels
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseFragment
import com.wzg.mixmusic.R
import com.wzg.mixmusic.databinding.FragmentNowPlayingBinding
import com.wzg.mixmusic.utils.InjectorUtils
import com.wzg.mixmusic.vm.NowPlayingFragmentViewModel

/**
 * describe 正在播放的音乐界面.
 *
 * @author wangzhangang
 * @date 2021/6/2 11:45 上午
 */
class NowPlayingFragment : BaseFragment(R.layout.fragment_now_playing) {
    val binding by viewbind<FragmentNowPlayingBinding>()
    val nowPlayingViewMode by viewModels<NowPlayingFragmentViewModel> {
        InjectorUtils.provideNowPlayingViewModel(requireContext())
    }

    override fun initView() {

    }
}