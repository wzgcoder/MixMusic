package com.wzg.mixmusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hi.dhl.binding.viewbind
import com.wzg.mixmusic.databinding.FragmentFindBinding
import com.wzg.mixmusic.vm.MusicFindViewModel

class MusicFindFragment : Fragment() {

    private val dashboardViewModel: MusicFindViewModel by viewModels()
    private val binding: FragmentFindBinding by viewbind()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }

}