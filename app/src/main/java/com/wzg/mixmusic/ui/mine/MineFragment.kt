package com.wzg.mixmusic.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hi.dhl.binding.viewbind
import com.wzg.mixmusic.databinding.FragmentMineBinding

class MineFragment : Fragment() {

    private val mineViewModel: MineViewModel by viewModels()
    private val binding: FragmentMineBinding by viewbind()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val textView: TextView = binding.textNotifications
        mineViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }


}