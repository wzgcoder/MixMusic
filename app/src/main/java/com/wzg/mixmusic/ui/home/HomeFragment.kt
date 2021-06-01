package com.wzg.mixmusic.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseFragment
import com.wzg.mixmusic.Constant
import com.wzg.mixmusic.databinding.FragmentHomeBinding
import com.wzg.mixmusic.ui.playlist.PlaylistActivity
import com.wzg.mixmusic.vm.HomeViewModel

class HomeFragment : BaseFragment() {
    private val binding: FragmentHomeBinding by viewbind()
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun initView() {
        binding.textHome.setOnClickListener {
            Intent(requireContext(), PlaylistActivity::class.java).apply {
                putExtra(Constant.PLAYLIST_ID_KEY, "24381616")
                startActivity(this)
            }

        }
    }

    override fun initData() {
    }
}