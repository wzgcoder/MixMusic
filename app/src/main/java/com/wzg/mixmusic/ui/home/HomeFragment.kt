package com.wzg.mixmusic.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hi.dhl.binding.viewbind
import com.wzg.library.base.BaseFragment
import com.wzg.mixmusic.databinding.FragmentHomeBinding

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
        homeViewModel.loginInfo.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
    }

    override fun initData() {
        super.initData()
        homeViewModel.phoneLogin("17601618067", "wangzg88521")
    }

}